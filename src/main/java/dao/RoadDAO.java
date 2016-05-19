/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Road;

/**
 *
 * @author koenv
 */
public interface RoadDAO {
	public void createRoad(Road road);
	
	public List<Road> getAllRoads();
	
	public Road getRoad(String name);
}
