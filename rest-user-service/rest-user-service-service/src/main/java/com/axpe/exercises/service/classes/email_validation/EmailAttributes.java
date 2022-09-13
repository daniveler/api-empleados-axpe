package com.axpe.exercises.service.classes.email_validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EmailAttributes
{
	@JsonProperty("mx_records") private String mxRecords;
	@JsonProperty("smtp_server") private String smtpServer;
	@JsonProperty("smtp_check") private String smtpCheck;
	@JsonProperty("accept_all") private String acceptAll;
	@JsonProperty("block") private String block;
	@JsonProperty("email") private String email;
	@JsonProperty("gibberish") private String gibberish;
	@JsonProperty("disposable") private String disposable;
	@JsonProperty("webmail") private String webmail;
	@JsonProperty("result") private String result;
	@JsonProperty("score") private String score;
	@JsonProperty("regex") private boolean regex;
	@JsonProperty("status") private String status;
}
