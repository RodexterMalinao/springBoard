package com.bomwebportal.lts.dao.image;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class WImageDisplayLkupDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 1512517875324909762L;

	private String imageId; // W_IMAGE_DISPLAY_LKUP.IMAGE_ID
	private String locale; // W_IMAGE_DISPLAY_LKUP.LOCALE
	private String imageName; // W_IMAGE_DISPLAY_LKUP.IMAGE_NAME
	private String imgPath; // W_IMAGE_DISPLAY_LKUP.IMG_PATH
	private OraDate createDate = new OraDateCreateDate();; // W_IMAGE_DISPLAY_LKUP.CREATE_DATE
	private String createBy; // W_ITEM_DISPLAY_LKUP.CREATE_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // W_ITEM_DISPLAY_LKUP.LAST_UPD_DATE
	private String lastUpdBy; // W_ITEM_DISPLAY_LKUP.LAST_UPD_BY

	
	public WImageDisplayLkupDAO() {
		primaryKeyFields = new String[] { "imageId", "locale" };
	}

	public String getTableName() {
		return "W_IMAGE_DISPLAY_LKUP";
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

}
