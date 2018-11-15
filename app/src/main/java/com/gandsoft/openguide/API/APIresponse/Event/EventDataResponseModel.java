package com.gandsoft.openguide.API.APIresponse.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventDataResponseModel {

    public String status;
    public String event_id;
    public String version_data;
    public List<EventTheEvent> the_event = new ArrayList<>();
    public List<EventHomeContent> homecontent = new ArrayList<>();
    public List<EventGallery> gallery = new ArrayList<>();
    public List<Map<Integer, List<EventPlaceList>>> place_list = new ArrayList<>();
    public List<EventDataContact> data_contact = new ArrayList<>();
    public List<EventCommitteeNote> committee_note = new ArrayList<>();
    public List<EventAbout> about = new ArrayList<>();
    public List<Map<String, List<EventScheduleListDate>>> schedule_list = new ArrayList<>();
    public List<EventWalletdata> wallet_data = new ArrayList<>();
    public List<EventEmergencies> emergencies = new ArrayList<>();
    public List<EventImportanInfo> importan_info = new ArrayList<>();
    public List<EventImportanInfoNew> importan_info_new = new ArrayList<>();
    public List<EventSurroundingArea> surrounding_area = new ArrayList<>();
    public List<EventSurroundingAreaNew> surrounding_area_new = new ArrayList<>();
    public String feedback_data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getVersion_data() {
        return version_data;
    }

    public void setVersion_data(String version_data) {
        this.version_data = version_data;
    }

    public List<EventTheEvent> getThe_event() {
        return the_event;
    }

    public void setThe_event(List<EventTheEvent> the_event) {
        this.the_event = the_event;
    }

    public List<EventHomeContent> getHomecontent() {
        return homecontent;
    }

    public void setHomecontent(List<EventHomeContent> homecontent) {
        this.homecontent = homecontent;
    }

    public List<EventGallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<EventGallery> gallery) {
        this.gallery = gallery;
    }

    public List<Map<Integer, List<EventPlaceList>>> getPlace_list() {
        return place_list;
    }

    public void setPlace_list(List<Map<Integer, List<EventPlaceList>>> place_list) {
        this.place_list = place_list;
    }

    public List<EventDataContact> getData_contact() {
        return data_contact;
    }

    public void setData_contact(List<EventDataContact> data_contact) {
        this.data_contact = data_contact;
    }

    public List<EventCommitteeNote> getCommittee_note() {
        return committee_note;
    }

    public void setCommittee_note(List<EventCommitteeNote> committee_note) {
        this.committee_note = committee_note;
    }

    public List<EventAbout> getAbout() {
        return about;
    }

    public void setAbout(List<EventAbout> about) {
        this.about = about;
    }

    public List<Map<String, List<EventScheduleListDate>>> getSchedule_list() {
        return schedule_list;
    }

    public void setSchedule_list(List<Map<String, List<EventScheduleListDate>>> schedule_list) {
        this.schedule_list = schedule_list;
    }

    public List<EventWalletdata> getWallet_data() {
        return wallet_data;
    }

    public void setWallet_data(List<EventWalletdata> wallet_data) {
        this.wallet_data = wallet_data;
    }

    public List<EventEmergencies> getEmergencies() {
        return emergencies;
    }

    public void setEmergencies(List<EventEmergencies> emergencies) {
        this.emergencies = emergencies;
    }

    public List<EventImportanInfo> getImportan_info() {
        return importan_info;
    }

    public void setImportan_info(List<EventImportanInfo> importan_info) {
        this.importan_info = importan_info;
    }

    public List<EventImportanInfoNew> getImportan_info_new() {
        return importan_info_new;
    }

    public void setImportan_info_new(List<EventImportanInfoNew> importan_info_new) {
        this.importan_info_new = importan_info_new;
    }

    public List<EventSurroundingArea> getSurrounding_area() {
        return surrounding_area;
    }

    public void setSurrounding_area(List<EventSurroundingArea> surrounding_area) {
        this.surrounding_area = surrounding_area;
    }

    public List<EventSurroundingAreaNew> getSurrounding_area_new() {
        return surrounding_area_new;
    }

    public void setSurrounding_area_new(List<EventSurroundingAreaNew> surrounding_area_new) {
        this.surrounding_area_new = surrounding_area_new;
    }

    public String getFeedback_data() {
        return feedback_data;
    }

    public void setFeedback_data(String feedback_data) {
        this.feedback_data = feedback_data;
    }
}
