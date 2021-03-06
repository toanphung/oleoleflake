package com.github.rerorero.oleoleflake.field;

import com.github.rerorero.oleoleflake.bitset.LongCodec;
import com.github.rerorero.oleoleflake.epoch.TimestampGenerator;
import org.junit.Test;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

public class TimestampFieldTest {

    long now = ZonedDateTime.now().toEpochSecond();
    TimestampGenerator<Long> mockedTimestampGenerator = new TimestampGenerator<Long>() {
        @Override
        public Instant timeGen() {
            return Instant.ofEpochSecond(now);
        }

        @Override
        public Long instantToTimestamp(Instant instant) {
            return TimestampGenerator.currentTimeSecondsGenerator.instantToTimestamp(instant);
        }

        @Override
        public Instant timestampToInstant(Long epoch) {
            return TimestampGenerator.currentTimeSecondsGenerator.timestampToInstant(epoch);
        }
    };

    @Test
    public void timestampTest() {
        TimestampField sut1 = new TimestampField(4, 4, 8, LongCodec.singleton, now - 4, mockedTimestampGenerator, false);
        assertEquals(Long.valueOf(now), sut1.currentTimestamp());
        assertEquals(4L, sut1.putField(0L, Long.valueOf(now)));
        assertEquals(8L, sut1.putField(0L, Long.valueOf(now+4)));
        assertEquals(Long.valueOf(now-4), sut1.getField(0L));
        assertEquals(Long.valueOf(now+4), sut1.getField(8L));

        assertEquals(Long.valueOf(now-4), sut1.getTimestampMin());
        assertEquals(Long.valueOf(now+11), sut1.getTimestampMax());
    }
}
