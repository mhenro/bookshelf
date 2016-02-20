package ru.khadzhinov.bookshelf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ru.khadzhinov.bookshelf.entity.Shelf;

@Repository
@Transactional
@Service("shelfService")
public class ShelfServiceImpl implements IShelfService {
    private final IShelfRepository shelfRepository;

    @Autowired
    public ShelfServiceImpl(IShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

	@Override
	public Shelf getShelfById(long id) {
		return shelfRepository.findOne(id);
	}

	@Override
	public List<Shelf> getAllShelves() {
		return Lists.newArrayList(shelfRepository.findAll());
	}

	@Override
	public Shelf save(Shelf shelf) {
		return shelfRepository.save(shelf);
	}    
}