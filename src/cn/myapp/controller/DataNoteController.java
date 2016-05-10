package cn.myapp.controller;

import java.text.ParseException;
import java.util.HashMap;

import com.jfinal.core.Controller;

import cn.myapp.model.ResultObj;
import xtc.data.fetch.FetchGsdata;
import xtc.info.display.EmailContentDisplay;
import xtc.model.gsdata.Nickname;

public class DataNoteController extends Controller {
	
	/**
	 * [获取群发信息] 标题,内容
	 * @return returnData : {title,detail}
	 * @throws ParseException 
	 */
	public void fetchGroupSendInfo() throws ParseException {
		
		FetchGsdata fetcher = new FetchGsdata() ;	
		// GET SUBAO DETAIL NICKNAME INFO .
		Nickname nickname = fetcher.fetchSubaoNickname() ; 
		// EMAIL CONTENT .
		EmailContentDisplay display = new EmailContentDisplay() ;
		String subaoDetailInfo = display.getEmailContentWillDisplay(nickname) ;
		
		// GET SORT INFO
		String dayString = fetcher.fetchDateString(nickname) ;
		String sortInfo = fetcher.fetchSortFromTwoDaysAgo(dayString) ;
		String emailContentStr = sortInfo + subaoDetailInfo ; // fetch detail .
		
		if (emailContentStr != null) {					
			// EMIAL TITLE .
			String sEmailTitle = "【通知】" + nickname.getResult_day() + "数据分析" ;		
			
			HashMap<String, Object> map = new HashMap<String, Object>() ;
			map.put("title", sEmailTitle) ;
			map.put("detail", emailContentStr) ;
			
			ResultObj resultObj = new ResultObj(map) ;
			renderJson(resultObj) ;				
		}		
				
	}
	
}
