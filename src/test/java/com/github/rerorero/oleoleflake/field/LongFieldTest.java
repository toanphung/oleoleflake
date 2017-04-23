package com.github.rerorero.oleoleflake.field;

import com.github.rerorero.oleoleflake.bitset.LongCodec;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LongFieldTest {

    @Test
    public void getFieldTest() throws Exception {
        BitSetField sut1 = new LongField<Long>(1,4, 64, LongCodec.singleton);    // _xxxx___ ________ ________ ________ ________ ________ ________ ________
        BitSetField sut2 = new LongField<Long>(8,30, 64, LongCodec.singleton);   // ________ xxxxxxxx xxxxxxxx xxxxxxxx xxxxxx__ ________ ________ ________

        assertEquals(0b0011100000000000000000000000000000000000000000000000000000000000L, sut1.putField(0L,7L));
        assertEquals(0b0000000010000000110000000010101010111000000000000000000000000000L, sut2.putField(0L,0b100000001100000000101010101110L));
        assertEquals(0b0011100010000000110000000010101010111000000000000000000000000000L, sut2.putField(sut1.putField(0L,7L), 0b100000001100000000101010101110L));
    }

    @Test
    public void putFieldTest() throws Exception {
        BitSetField sut1 = new LongField<Long>(1,4, 64, LongCodec.singleton);    // _xxxx___ ________ ________ ________ ________ ________ ________ ________
        BitSetField sut2 = new LongField<Long>(8,30, 64, LongCodec.singleton);   // ________ xxxxxxxx xxxxxxxx xxxxxxxx xxxxxx__ ________ ________ ________

        assertEquals(7L, sut1.getField(0b1011110010000000110000000010101010111100000000000000000000000000L));
        assertEquals(0b100000001100000000101010101110L, sut2.getField(0b1011100010000000110000000010101010111000011100000010100000000000L));
    }
}
