package com.csl.bmsri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.csl.bmsri.JPA.AttRemJPA;
import com.csl.bmsri.JPA.AttnJPA;
import com.csl.bmsri.Models.Attn_App_Info;
import com.csl.bmsri.Models.Attn_Remarks;

@Controller
public class AttendanceController {
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private AttnJPA attnjpa;
	
	@Autowired
	private AttRemJPA attnremjpa;
	
	@RequestMapping("/attendance")
	public ModelAndView index()
	{
		ModelAndView model = new ModelAndView("/pages/attendance/index");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		
		List<Attn_Remarks> remlist = attnremjpa.findAll();
		
		//remlist = remlist.stream().map(element -> element.getRemarks().toUpperCase());
		
		Attn_App_Info attnapp = new Attn_App_Info();
		String name = user.getName();
		model.addObject("name",name);
		attnapp.setName(name);
		model.addObject("remarkslist",remlist);
		model.addObject("attnobj",attnapp);
		return model;
	}
	
	
	@RequestMapping("/attendance/save")
	public String save(@ModelAttribute("") Attn_App_Info attninfo, @Param("attnremid") String attnremid)
	{
		
		attninfo.setAttn_status("Y");
	    
	    int attnremmid = Integer.parseInt(attnremid);
	    attninfo.setAttnremid(attnremmid);
	    System.out.println("get remid: "+attnremmid);
		
		Date date = new Date();
	    String strDateFormat = "hh:mm:ss";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate= dateFormat.format(date);
	    attninfo.setTime(formattedDate);
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");  
	    Date dates = new Date();  
	    System.out.println(formatter.format(dates));
	    
	    System.out.println(dates);
	    
	    attninfo.setDate(dates);
	    
	    attnjpa.save(attninfo);
		System.out.println("Attendance Saved");
		return "redirect:/attendance";
	}

}
