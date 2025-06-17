package com.etshn.stock.service;

import java.sql.Date;

import com.etshn.stock.payload.CachierMouvmentsDto;
import com.etshn.stock.payload.CachierMouvmentsResponce;

public interface CachierMouvementsService {
	CachierMouvmentsDto add(CachierMouvmentsDto mDto);
    CachierMouvmentsResponce getByInterval(Date startDate, Date andDate,int pageNo, int pageSize, String sortBy, String sortDir);
}
