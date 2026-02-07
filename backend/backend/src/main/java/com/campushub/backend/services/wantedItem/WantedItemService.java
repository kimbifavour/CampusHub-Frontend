package com.campushub.backend.services.wantedItem;

import com.campushub.backend.models.wanteditems.WantedItem;
import com.campushub.backend.repositories.wantedItem.WantedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WantedItemService {

    @Autowired
    WantedItemRepository wantedItemRepository;

    public WantedItem createWantedItem(WantedItem wantedItem) {
        return wantedItemRepository.save(wantedItem);
    }

    public List<WantedItem> getAllWantedItems() {
        return wantedItemRepository.findAll();
    }

    public List<WantedItem> getAllWantedItemsByUser(UUID userId) {
        return wantedItemRepository.findByUser_Id(userId);
    }

    public List<WantedItem> getAllWantedItemsByCategory(String categoryName) {
        return wantedItemRepository.findByCategory_Name(categoryName);
    }

    public WantedItem deleteWantedItemById(UUID itemId) {
        WantedItem wantedItem = wantedItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new RuntimeException("Wanted item not found with id: " + itemId)
                );
        wantedItemRepository.delete(wantedItem);
        return wantedItem;
    }
}
