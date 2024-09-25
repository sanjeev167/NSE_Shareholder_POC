package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.DownloadFileModel;
import com.nseit.shareholder1.model.PostCal;
import com.nseit.shareholder1.model.WeightedAverageModel;
import com.nseit.shareholder1.service.ReportsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reports")
@Slf4j
public class ReportsController {
	@Autowired
	ReportsService reportsService;

//	@RequestMapping(value="/prePostCalBenpose/{version}",method=RequestMethod.GET)
//	public ResponseEntity<?> prePostCalBenpose(@PathVariable String version){
//		return reportsService.prePostCalBenpose(version);
//	}
//	
//	@RequestMapping(value="/prePostCalShare/{version}",method=RequestMethod.GET)
//	public ResponseEntity<?> prePostCalShare(@PathVariable String version){
//		return reportsService.prePostCalShare(version);
//	}

	@RequestMapping(value = "/getAboveLimitLevel1/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> limitLevel1(@PathVariable String version) {
		return reportsService.getAboveLimitLevel1(version);
	}

	@RequestMapping(value = "/getBelowLimitLevel1/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getBelowLimitLevel1(@PathVariable String version) {
		return reportsService.getBelowLimitLevel1(version);
	}

//	@RequestMapping(value="/postCal/{version}",method=RequestMethod.POST)
//	public ResponseEntity<?> postCal(@PathVariable String version,@RequestBody PostCal postCal){
//		return reportsService.postCal(version,postCal);
//	}
//	
//	@RequestMapping(value="/preCal/{version}",method=RequestMethod.GET)
//	public ResponseEntity<?> preCal(@PathVariable String version){
//		return reportsService.preCal(version);
//	}

	@RequestMapping(value = "/cafListing/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> cafListing(@PathVariable String version) {
		return reportsService.cafListing(version);
	}

	@RequestMapping(value = "/cafGeneration/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> cafGeneration(@PathVariable String version, @RequestBody PostCal postCal) {
		return reportsService.cafGeneration(version, postCal);
	}

	@RequestMapping(value = "/mimps/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> writeCellsMimps(@PathVariable String version, @RequestParam("quater") String month,
			@RequestParam("year") String year) {
		return reportsService.writeCellsMimps(version, month, year);
	}

	@RequestMapping(value = "/topTenShareholders/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> topTenShareholders(@PathVariable String version) {
		return reportsService.topTenShareholders(version);
	}
	
	@RequestMapping(value = "/shareHoldingDistribution/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> shareHoldingDistribution(@PathVariable String version) {
		return reportsService.shareHoldingDistribution(version);
	}
	
	@RequestMapping(value = "/cag/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> cag(@PathVariable String version) {
		return reportsService.cag(version);
	}
	
	@RequestMapping(value = "/cagDisplay/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> cagDisplay(@PathVariable String version) {
		return reportsService.cagDisplay(version);
	}
	
	@RequestMapping(value = "/salesPurchase/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> salesPurchase(@PathVariable String version, @RequestParam("month") Integer month, @RequestParam("year") Integer year) {
		return reportsService.salesPurchase(version, month, year);
	}
	
	@RequestMapping(value = "/transferDetailsReport/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> transferDetailsReport(@PathVariable String version, @RequestParam("date") String date) {
		return reportsService.transferDetailsReport(version, date);
	}
	
	@RequestMapping(value = "/sendRta/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> sendRta(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return reportsService.sendRta(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/nsdlDownloadFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> nsdlDownloadFile(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return reportsService.nsdlDownloadFile(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/cdslDownloadFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> cdslDownloadFile(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return reportsService.cdslDownloadFile(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/beneficiaryDetailsDownloadFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> beneficiaryDetailsDownloadFile(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return reportsService.beneficiaryDetailsDownloadFile(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/salesPurchasesDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> salesPurchasesDownload(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return reportsService.salesPurchasesDownload(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/mimpsDownloadFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> mimpsDownloadFile(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return reportsService.mimpsDownloadFile(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/pacReport/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> pacReport(@PathVariable String version) {
		return reportsService.pacReport(version);
	}
	
	@RequestMapping(value = "/transferMasterExport/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> transferMasterExport(@PathVariable String version) {
		return reportsService.transferMasterExport(version);
	}
	
	@RequestMapping(value = "/transferMasterExportDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> transferMasterExportDownload(@PathVariable String version,@RequestBody DownloadFileModel downloadFileModel) {
		return reportsService.transferMasterExportDownload(version,downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/pacReportDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> pacReportDownload(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel) {
		return reportsService.pacReportDownload(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/transferDetailsReportDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> transferDetailsReportDownload(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel) {
		return reportsService.transferDetailsReportDownload(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/cagDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> cagDownload(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel) {
		return reportsService.cagDownload(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/shareHoldingPatternTemplate/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> shareHoldingPatternTemplate(@PathVariable String version) {
		return reportsService.shareHoldingPatternTemplate(version);
	}
	
	@RequestMapping(value = "/getTaxTeamReport1/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getTaxTeamReport1(@PathVariable String version) {
		return reportsService.getTaxTeamReport1(version);
	}
	
	@RequestMapping(value = "/taxTeamDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> taxTeamDownload(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel) {
		return reportsService.taxTeamDownload(version, downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/getWeightedAverage/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getWeightedAverage(@PathVariable String version, @RequestBody WeightedAverageModel weightedAverageModel) {
		return reportsService.getWeightedAverage(version,weightedAverageModel);
	}
	
	@RequestMapping(value = "/getWeightedAverageDownload/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getWeightedAverageDownload(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel) {
		return reportsService.getWeightedAverageDownload(version, downloadFileModel.getId());
	}
}
