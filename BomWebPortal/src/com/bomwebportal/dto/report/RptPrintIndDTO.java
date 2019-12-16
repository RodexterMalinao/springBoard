package com.bomwebportal.dto.report;


public class RptPrintIndDTO {
	private boolean hasHandsetTradeIn;
	private boolean hasIGuard;
	private boolean iGuardLDS;
	private boolean iGuardAD;
	private boolean iGuardUAD;
	private boolean hasMobileSafePhoneSafety;
	private boolean hasSecretarialService;
	private boolean hasTravelInsurance;
	private boolean hasHelperCareInsurance;
	private boolean hasProjectEagleInsurance;

	public RptPrintIndDTO() {
		this.hasHandsetTradeIn = false;
		this.hasIGuard = false;
		this.iGuardLDS = false;
		this.iGuardAD = false;
		this.iGuardUAD = false;
		this.hasMobileSafePhoneSafety = false;
		this.hasSecretarialService = false;
		this.hasTravelInsurance = false;
		this.hasHelperCareInsurance = false;
		this.setHasProjectEagleInsurance(false);
	}
	
	public boolean hasHandsetTradeIn() {
		return hasHandsetTradeIn;
	}
	public void setHasHandsetTradeIn(boolean hasHandsetTradeIn) {
		this.hasHandsetTradeIn = hasHandsetTradeIn;
	}
	public boolean hasIGuard() {
		return hasIGuard;
	}
	public void setHasIGuard(boolean hasIGuard) {
		this.hasIGuard = hasIGuard;
	}
	public boolean hasMobileSafePhoneSafety() {
		return hasMobileSafePhoneSafety;
	}
	public void setHasMobileSafePhoneSafety(boolean hasMobileSafePhoneSafety) {
		this.hasMobileSafePhoneSafety = hasMobileSafePhoneSafety;
	}
	public boolean hasSecretarialService() {
		return hasSecretarialService;
	}
	public void setHasSecretarialService(boolean hasSecretarialService) {
		this.hasSecretarialService = hasSecretarialService;
	}
	public boolean isiGuardLDS() {
		return iGuardLDS;
	}
	public void setiGuardLDS(boolean iGuardLDS) {
		this.iGuardLDS = iGuardLDS;
	}
	public boolean isiGuardAD() {
		return iGuardAD;
	}
	public void setiGuardAD(boolean iGuardAD) {
		this.iGuardAD = iGuardAD;
	}

	public boolean isiGuardUAD() {
		return iGuardUAD;
	}

	public void setiGuardUAD(boolean iGuardUAD) {
		this.iGuardUAD = iGuardUAD;
	}

	public boolean isHasTravelInsurance() {
		return hasTravelInsurance;
	}

	public void setHasTravelInsurance(boolean hasTravelInsurance) {
		this.hasTravelInsurance = hasTravelInsurance;
	}

	public boolean isHasHelperCareInsurance() {
		return hasHelperCareInsurance;
	}

	public void setHasHelperCareInsurance(boolean hasHelperCareInsurance) {
		this.hasHelperCareInsurance = hasHelperCareInsurance;
	}

	public boolean isHasProjectEagleInsurance() {
		return hasProjectEagleInsurance;
	}

	public void setHasProjectEagleInsurance(boolean hasProjectEagleInsurance) {
		this.hasProjectEagleInsurance = hasProjectEagleInsurance;
	}
}
