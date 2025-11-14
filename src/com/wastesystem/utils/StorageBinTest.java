package com.wastesystem.utils;

import com.wastesystem.storage.StorageBin;
import com.wastesystem.storage.WasteItem;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for StorageBin class.
 * Validates correct waste sorting and exception handling.
 */
public class StorageBinTest {

    @Test
    public void testAddCorrectItem() throws SystemException {
        StorageBin bin = new StorageBin(WasteItem.Type.PLASTIC, 3);
        WasteItem item = new WasteItem(WasteItem.Type.PLASTIC, 1.0);
        bin.addItem(item);
        assertEquals(1.0, bin.getCurrentSize(), 0.01);
    }

    @Test
    public void testAddMultipleItems() throws SystemException {
        StorageBin bin = new StorageBin(WasteItem.Type.METAL, 3);
        bin.addItem(new WasteItem(WasteItem.Type.METAL, 1.2));
        bin.addItem(new WasteItem(WasteItem.Type.METAL, 0.8));
        assertEquals(2.0, bin.getCurrentSize(), 0.01);
    }

    @Test(expected = SystemException.class)
    public void testAddWrongItem() throws SystemException {
        StorageBin bin = new StorageBin(WasteItem.Type.PAPER, 3);
        WasteItem wrongItem = new WasteItem(WasteItem.Type.PLASTIC, 1.0);
        bin.addItem(wrongItem); // should throw SystemException
    }

    @Test(expected = SystemException.class)
    public void testBinCapacityLimit() throws SystemException {
        StorageBin bin = new StorageBin(WasteItem.Type.ORGANIC, 2);
        bin.addItem(new WasteItem(WasteItem.Type.ORGANIC, 1.0));
        bin.addItem(new WasteItem(WasteItem.Type.ORGANIC, 1.5)); // should throw SystemException
    }
}
