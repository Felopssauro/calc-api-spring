package com.github.felopssauro.calc_api_spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

/**
 * Tracks how many times the same operand 'a' was used with different values of 'b'
 * for a given operation within a sliding 5-minute window. 
 * 
 * This is similar to tracking failed login attempts: "how many different passwords 
 * were tried with the same email in the last 5 minutes."
 *
 * Key format: usage-tracker:{operation}:{a}
 * Each unique 'b' value is stored as a member with its timestamp as the score.
 */
@Service
public class OperatorUsageTracker {

    private static final Logger log = LoggerFactory.getLogger(OperatorUsageTracker.class);
    private static final String KEY_PREFIX = "usage-tracker:";
    private static final Duration WINDOW = Duration.ofMinutes(5);

    private final StringRedisTemplate redisTemplate;

    public OperatorUsageTracker(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Records a usage of the given operation with operand 'a' and 'b'.
     * Tracks how many different 'b' values have been used with the same 'a' in the window.
     *
     * @return the number of distinct 'b' values used with this 'a' in the last 5 minutes
     */
    public long recordUsage(String operation, int a, int b) {
        String key = buildKey(operation, a);
        long now = Instant.now().toEpochMilli();

        ZSetOperations<String, String> zOps = redisTemplate.opsForZSet();

        // Use 'b' as the member - this ensures each distinct 'b' only appears once
        // The score is the timestamp of the most recent usage of this 'b'
        String member = String.valueOf(b);
        zOps.add(key, member, now);

        // Remove entries outside the 5-minute window
        long windowStart = now - WINDOW.toMillis();
        zOps.removeRangeByScore(key, 0, windowStart);

        // Set key expiry so Redis doesn't hold stale keys forever
        redisTemplate.expire(key, WINDOW.plusMinutes(1));

        // Return count of distinct 'b' values within the window
        Long count = zOps.zCard(key);
        long usageCount = count != null ? count : 0;

        log.info("Usage tracked: operation={}, operandA={}, operandB={}, distinctBCount={} in last 5 minutes",
                operation, a, b, usageCount);

        return usageCount;
    }

    /**
     * Returns the number of distinct 'b' values used with operand 'a' for the given operation
     * within the last 5 minutes, without recording a new usage.
     */
    public long getUsageCount(String operation, int a) {
        String key = buildKey(operation, a);
        long now = Instant.now().toEpochMilli();
        long windowStart = now - WINDOW.toMillis();

        ZSetOperations<String, String> zOps = redisTemplate.opsForZSet();

        // Prune expired entries first
        zOps.removeRangeByScore(key, 0, windowStart);

        Long count = zOps.zCard(key);
        return count != null ? count : 0;
    }

    /**
     * Returns all distinct 'b' values used with operand 'a' within the current window.
     * Useful for debugging or detailed analytics.
     */
    public Set<String> getDistinctBValues(String operation, int a) {
        String key = buildKey(operation, a);
        long now = Instant.now().toEpochMilli();
        long windowStart = now - WINDOW.toMillis();

        ZSetOperations<String, String> zOps = redisTemplate.opsForZSet();

        // Prune expired entries
        zOps.removeRangeByScore(key, 0, windowStart);

        return zOps.rangeByScore(key, windowStart, now);
    }

    private String buildKey(String operation, int a) {
        return KEY_PREFIX + operation + ":" + a;
    }
}
