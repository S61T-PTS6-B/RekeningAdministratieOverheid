/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import model.CarOwner;
import model.CarTracker;
import model.MileageRate;
import model.NAW;
import service.ICarOwnerService;
import service.ICarTrackerService;
import service.IMileageRateService;
import service.INAWService;

/**
 *
 * @author koenv
 */
@WebServlet(name = "CarTrackerAdm", urlPatterns = {"/CarTrackerAdm",
	"/Manage",
	"/FillCT",
	"/ManageMileage",
	"/AddMileage",
	"/AddPerson",
	"/AddCarTracker",
	"/CarTrackerList",
	"/NawList",
	"/MileageList",
	"/PersonalData",
	"/ChangeFirstname",
	"/ChangeLastname",
	"/ChangeAddress",
	"/ChangeNumber",
	"/ChangeZipcode",
	"/ChangeCity",
	"/ChangeTelephone",
	"/ChangeMail",
	"/ChangeCT",
	"/ChangeMA",
	"/CarBrandChange",
	"/CarModelChange",
	"/LicenseChange",
	"/PrizeCategoryChange",
	"/MileageChange",
	"/RegioChange",
	"/CategoryChange",
	"/IntervalChange"})
public class DBServlet extends HttpServlet {

	@Inject
	INAWService ns;

	@Inject
	ICarTrackerService cts;

	@Inject
	ICarOwnerService cos;

	@Inject
	IMileageRateService mrs;

	private int bsn;
	private String id;

	protected void processRequest(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		String userPath = req.getServletPath();

		switch (userPath) {
			case "/Manage": {

				req.setAttribute("naws", ns.getAllNaws());
				req.setAttribute("countnaws", ns.getAllNaws().size());
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/manage.jsp");
				view.forward(req, res);
				break;
			}
			case "/ManageMileage": {
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/managemilage.jsp");
				view.forward(req, res);
				break;
			}
			case "/AddPerson": {
				req.setAttribute("naws", ns.getAllNaws());
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/manage.jsp");
				view.forward(req, res);
				break;
			}
			case "/CarTrackerList": {
				req.setAttribute("CTs", cts.getAllCarTrackers());
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/cartrackerlist.jsp");
				view.forward(req, res);
				break;
			}
			case "/NawList": {
				req.setAttribute("naws", ns.getAllNaws());
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/NawList.jsp");
				view.forward(req, res);
				break;
			}
			case "/MileageList": {
				List<MileageRate> mars = mrs.getAllRates();
				req.setAttribute("MAR", mars);
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/mileagelist.jsp");
				view.forward(req, res);
				break;
			}
			case "/ChangeCT": {
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/changecartracker.jsp");
				view.forward(req, res);
				break;
			}
			case "/ChangeMA": {
				RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/changemileage.jsp");
				view.forward(req, res);
				break;
			}

		}

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		String userPath = req.getServletPath();

		if (userPath.equals("/Manage")) {
			String json = null;
			String OptionBSN = req.getParameter("OptionBSN").trim();
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW fix = ns.getNAWByBsn(Integer.parseInt(OptionBSN));
			if (OptionBSN != null) {
				jsonMap.put("bsn", OptionBSN);
				jsonMap.put("firstname", fix.getFirstname());
				jsonMap.put("lastname", fix.getLastname());
				jsonMap.put("address", fix.getAddress());
				jsonMap.put("housenumber", fix.getNumber());
				jsonMap.put("zipcode", fix.getZipcode());
				jsonMap.put("city", fix.getCity());
				jsonMap.put("telephone", fix.getTelephone());
				jsonMap.put("email", fix.getEmail());

				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}

		}
		if (userPath.equals("FillCT")) {
			String json = null;
			String OptionBSN = req.getParameter("OptionBSN").trim();
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW fix = ns.getNAWByBsn(Integer.parseInt(OptionBSN));
			List<CarTracker> cartrackers = cts.getCarTrackerById(fix);
			if (OptionBSN != null) {
				json = new Gson().toJson(cartrackers, List.class);
				System.out.println("Dit is de CT json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/ChangeFirstname")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newfirstname = req.getParameter("NewFirstname");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeFirstname(incoming, newfirstname);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("firstname", incoming.getFirstname());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/ChangeLastname")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newlastname = req.getParameter("NewLastname");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeLastname(incoming, newlastname);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("lastname", incoming.getLastname());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/ChangeAddress")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newstreet = req.getParameter("NewAddress");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeAddress(incoming, newstreet);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("address", incoming.getAddress());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/ChangeNumber")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newnumber = req.getParameter("NewNumber");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeHouseNumber(incoming, newnumber);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("housenumber", incoming.getNumber());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}

		if (userPath.equals("/ChangeZipcode")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newzipcode = req.getParameter("NewZipcode");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeZipcode(incoming, newzipcode);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("zipcode", incoming.getZipcode());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}

		if (userPath.equals("/ChangeCity")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newcity = req.getParameter("NewCity");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeCity(incoming, newcity);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("city", incoming.getCity());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/ChangeTelephone")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newtelephone = req.getParameter("NewTelephone");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeTelephone(incoming, newtelephone);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("telephone", incoming.getTelephone());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/ChangeMail")) {
			String json = null;
			String newbsn = req.getParameter("BSN").trim();
			String newmail = req.getParameter("NewMail");
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			NAW incoming = ns.getNAWByBsn(Integer.parseInt(newbsn));
			incoming = ns.changeEmail(incoming, newmail);
			if (incoming != null) {
				jsonMap.put("bsn", newbsn);
				jsonMap.put("mail", incoming.getEmail());
				json = new Gson().toJson(jsonMap, Map.class);

				System.out.println("Dit is de veranderde json: " + json.toString());
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write(json);
			}
		}
		if (userPath.equals("/AddMileage")) {
			double rate = Double.parseDouble(req.getParameter("mar"));
			String regio = req.getParameter("regio");
			double category = Double.parseDouble(req.getParameter("pricecategory"));
			double interval = Double.parseDouble(req.getParameter("interval"));

			MileageRate mar = new MileageRate(rate, regio, category, interval);
			mrs.createMileageRate(mar);

			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/managemilage.jsp");
			view.forward(req, res);

		}
		if (userPath.equals("/AddPerson")) {
			int bsn = Integer.parseInt(req.getParameter("bsn"));
			String firstname = req.getParameter("firstname");
			String lastname = req.getParameter("lastname");
			String address = req.getParameter("address");
			String number = req.getParameter("number");
			String zipcode = req.getParameter("zipcode");
			String city = req.getParameter("city");
			String telephone = req.getParameter("telephone");
			String email = req.getParameter("email");
			NAW n = new NAW(bsn, firstname, lastname, address, number, zipcode, city, telephone, email);
			ns.createNAW(n);
			req.setAttribute("naws", ns.getAllNaws());
			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/manage.jsp");
			view.forward(req, res);
		}
		if (userPath.equals("/AddCarTracker")) {
			req.setAttribute("naws", ns.getAllNaws());
			bsn = Integer.parseInt(req.getParameter("bsn"));
			NAW naw = ns.getNAWByBsn(bsn);
			double category = Double.parseDouble(req.getParameter("category"));
			String license = req.getParameter("license");
			String carmodel = req.getParameter("carmodel");
			String carbrand = req.getParameter("carbrand");
			String startownershipstring = req.getParameter("startOwnership");
			DateFormat startownershipfix = new SimpleDateFormat("dd-MM-YYYY");
			Date startownership;
			try {
				startownership = startownershipfix.parse(startownershipstring);
				CarTracker ct = new CarTracker(category, license, carmodel, carbrand, true);
				cts.createCarTracker(ct);
				req.setAttribute("CTs", cts.getAllCarTrackers());
				CarOwner co = new CarOwner(ct, naw, startownership);
				cos.createCarOwner(co);
			} catch (ParseException ex) {
				Logger.getLogger(DBServlet.class.getName()).log(Level.SEVERE, null, ex);
			}

			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/manage.jsp");
			view.forward(req, res);
		}
		if (userPath.equals("/PersonalData")) {
			bsn = Integer.parseInt(req.getParameter("bsn"));

			NAW naw = ns.getNAWByBsn(bsn);
			ArrayList<CarTracker> ctList = cts.getCarTrackerById(naw);

			req.setAttribute("theCTs", ctList);
			req.setAttribute("theUser", naw);

			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			view.forward(req, res);
		}
		if (userPath.equals("/ChangeCT")) {
			long cid = Long.parseLong(req.getParameter("id"));

			CarTracker ct = cts.getSingleCarTrackerById(cid);

			req.setAttribute("ct", ct);
			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/changecartracker.jsp");
			view.forward(req, res);

		}
		if (userPath.equals("/ChangeMA")) {
			String id = req.getParameter("id");

			MileageRate mr = mrs.getRateById(id);

			req.setAttribute("mar", mr);
			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/pages/changemileage.jsp");
			view.forward(req, res);
		}
		if (userPath.equals("/PrizeCategoryChange")) {
			double category = Double.parseDouble(req.getParameter("category"));

			NAW naw = ns.getNAWByBsn(bsn);
			CarTracker ct = cts.getSingleCarTrackerByNaw(naw);

			req.setAttribute("theUser", ct);

			cts.changePrizeCategory(ct, category);

			req.setAttribute("theCT", ct);
			req.setAttribute("theUser", naw);
			ArrayList<CarTracker> ctList = cts.getCarTrackerById(naw);
			req.setAttribute("theCTs", ctList);

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);
		}
		if (userPath.equals("/LicenseChange")) {
			String license = req.getParameter("license");

			NAW naw = ns.getNAWByBsn(bsn);
			CarTracker ct = cts.getSingleCarTrackerByNaw(naw);
			req.setAttribute("theUser", ct);
			cts.changeLicense(ct, license);

			req.setAttribute("theCT", ct);
			req.setAttribute("theUser", naw);
			ArrayList<CarTracker> ctList = cts.getCarTrackerById(naw);
			req.setAttribute("theCTs", ctList);

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);
		}
		if (userPath.equals("/CarModelChange")) {
			String carmodel = req.getParameter("carmodel");

			NAW naw = ns.getNAWByBsn(bsn);
			CarTracker ct = cts.getSingleCarTrackerByNaw(naw);
			cts.changeModelCar(ct, carmodel);

			req.setAttribute("theCT", ct);
			req.setAttribute("theUser", naw);
			ArrayList<CarTracker> ctList = cts.getCarTrackerById(naw);
			req.setAttribute("theCTs", ctList);

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);
		}
		if (userPath.equals("/CarBrandChange")) {
			String carbrand = req.getParameter("carbrand");

			NAW naw = ns.getNAWByBsn(bsn);
			CarTracker ct = cts.getSingleCarTrackerByNaw(naw);
			cts.changeBrandCar(ct, carbrand);

			req.setAttribute("theCT", ct);
			req.setAttribute("theUser", naw);
			ArrayList<CarTracker> ctList = cts.getCarTrackerById(naw);
			req.setAttribute("theCTs", ctList);

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);
		}
		if (userPath.equals("/MileageChange")) {
			String id = req.getParameter("id");
			String mileageRate = req.getParameter("mileagerate");

			MileageRate mr = mrs.getRateById(id);
			mrs.changeMileageRate(mr, Double.parseDouble(mileageRate));

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);

		}
		if (userPath.equals("/RegioChange")) {
			String id = req.getParameter("id");
			String regio = req.getParameter("regio");

			MileageRate mr = mrs.getRateById(id);
			mrs.changeRegio(mr, regio);
			req.setAttribute("mar", mr);

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/changemileage.jsp");
			viewResult.forward(req, res);
		}
		if (userPath.equals("/CategoryChange")) {
			String id = req.getParameter("id");
			String pricecategory = req.getParameter("pricecategory");

			MileageRate mr = mrs.getRateById(id);
			mrs.changePrizeCategory(mr, Double.parseDouble(pricecategory));

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);
		}
		if (userPath.equals("/IntervalChange")) {
			String id = req.getParameter("id");
			String interval = req.getParameter("interval");

			MileageRate mr = mrs.getRateById(id);
			mrs.changeInterval(mr, Double.parseDouble(interval));

			RequestDispatcher viewResult = req.getRequestDispatcher("/WEB-INF/pages/personaldata.jsp");
			viewResult.forward(req, res);
		}
	}

}
