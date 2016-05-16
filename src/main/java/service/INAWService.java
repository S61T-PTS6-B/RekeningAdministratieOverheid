/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.NAW;

/**
 *
 * @author koenv
 */
public interface INAWService {

	public void createNAW(NAW naw);

	public List<NAW> getAllNaws();

	public NAW getNAWByBsn(int bsn);

	public NAW changeMail(NAW naw, String newmail);

	public NAW changePhone(NAW naw, String newphone);

	public NAW changeFirstname(NAW naw, String newfirstname);

	public NAW changeLastname(NAW naw, String newlastname);

	public NAW changeAddress(NAW naw, String newstreet);

	public NAW changeHouseNumber(NAW naw, String newnumber);
	
	public NAW changeZipcode(NAW naw, String newzipcode);
	
	public NAW changeCity(NAW naw, String newcity);
	
	public NAW changeTelephone(NAW naw, String newtelephone);
	
	public NAW changeEmail(NAW naw, String newmail);

}
