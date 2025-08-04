package com.example.service;

import java.util.Optional;

import com.example.model.Shelf;

public interface IShelf {
	public Shelf createOrUpdateShelf(Shelf shelf);
	 public Optional<Shelf> getShelfById(Integer id);
	 public Optional<Shelf> getShelfByUserId(Integer userId);
	 public void deleteShelfById(Integer id);
}
