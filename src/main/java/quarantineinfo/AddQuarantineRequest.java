package quarantineinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddQuarantineRequest {
    @JsonProperty("quarantineid")
    private int quarantineID;
    @JsonProperty("countryid")
    private int countryid;
    @JsonProperty("mask")
    private String mask;
    @JsonProperty("test")
    private int test;
    @JsonProperty("quarantine")
    private int quarantine;
    @JsonProperty("locatorform")
    private String locatorForm;
    @JsonProperty("healthform")
    private String healthForm;
    @JsonProperty("authorisation")
    private String authorisation;
    @JsonProperty("vaccination")
    private String vaccination;
    @JsonProperty("insurance")
    private String insurance;

    public AddQuarantineRequest() {
        quarantineID = 0;
        countryid = 0;
        mask = "";
        test = 0;
        quarantine = 0;
        locatorForm = "";
        healthForm = "";
        authorisation = "";
        vaccination = "";
        insurance = "";
    }

    public AddQuarantineRequest(int qid, int cid, String mask, int test, int quarantine, String locatorForm, String healthForm, String authorisation, String vaccination, String insurance) {
        this.quarantineID = qid;
        this.countryid = cid;
        this.mask = mask;
        this.test = test;
        this.quarantine = quarantine;
        this.locatorForm = locatorForm;
        this.healthForm = healthForm;
        this.authorisation = authorisation;
        this.vaccination = vaccination;
        this.insurance = insurance;
    }

    public int getCountryid() {
        return countryid;
    }

    public void setCountryid(int countryid) {
        this.countryid = countryid;
    }

    public int getQuarantine() {
        return quarantine;
    }

    public int getQuarantineID() {
        return quarantineID;
    }

    public String getAuthorisation() {
        return authorisation;
    }

    public int getTest() {
        return test;
    }

    public String getHealthForm() {
        return healthForm;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getLocatorForm() {
        return locatorForm;
    }

    public String getMask() {
        return mask;
    }

    public String getVaccination() {
        return vaccination;
    }

    public void setAuthorisation(String authorisation) {
        this.authorisation = authorisation;
    }

    public void setHealthForm(String healthForm) {
        this.healthForm = healthForm;
    }

    public void setQuarantine(int quarantine) {
        this.quarantine = quarantine;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setQuarantineID(int quarantineID) {
        this.quarantineID = quarantineID;
    }

    public void setLocatorForm(String locatorForm) {
        this.locatorForm = locatorForm;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public void setVaccination(String vaccination) {
        this.vaccination = vaccination;
    }
}
