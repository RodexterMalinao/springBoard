package com.bomwebportal.lts.wsExportLts;

import com.bomwebportal.lts.dto.export.ItemDetailProfileExportLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;

public interface ItemExportService {

	public abstract ItemDetailProfileExportLtsDTO[] mapOffersToItem(OfferDetailProfileLtsDTO[] pOfferDtls, String[] pIddCallPlanCds);
}
