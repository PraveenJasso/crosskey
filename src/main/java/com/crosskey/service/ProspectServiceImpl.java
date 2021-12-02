package com.crosskey.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crosskey.dto.ProspectDTO;
import com.crosskey.model.Prospect;
import com.crosskey.respository.ProspectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import ma.glasnost.orika.MapperFacade;

@Service
public class ProspectServiceImpl implements ProspectService {

	@Autowired
	private ProspectRepository prospectRepository;

	@Autowired
	private transient MapperFacade mapperFacade;

	@Override
	public ProspectDTO processSingleProspect(ProspectDTO prospectDTO) {
		Prospect prospect = mapperFacade.map(prospectDTO, Prospect.class);
		prospect.setResult(calculate(prospect.getInterest(), prospect.getTotalLoan(), prospect.getYears()));
		prospect.setId(UUID.randomUUID().toString());
		prospectRepository.save(prospect);
		return prospectDTO;
	}

	private String processFileUpload(MultipartFile file) {
		String filePath = null;
		if (file.isEmpty()) {
			return filePath;
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, bytes);
			filePath = path.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	@Override
	public List<ProspectDTO> processProspect(MultipartFile file) {

		String s = processFileUpload(file);
		List<Prospect> prospects = new ArrayList<>();
		if (s != null) {
			double yearlyInterest = 0;
			double totalLoan = 0;
			int years = 0;
			double E = 0;
			try {
				FileInputStream fstream = new FileInputStream(s);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				String strLine;
				br.readLine();
				while ((strLine = br.readLine()) != null) {
					String name;
					if (!strLine.isEmpty() && strLine.charAt(0) == '\"') {
						int lastIndexOfQuote = strLine.lastIndexOf('\"');
						name = strLine.substring(0, lastIndexOfQuote);
						String[] split = strLine.substring(lastIndexOfQuote + 2).split(",");
						totalLoan = Double.parseDouble(split[0]);
						yearlyInterest = Double.parseDouble(split[1]);
						years = Integer.parseInt(split[2]);
						E = calculate(yearlyInterest, totalLoan, years);
						Prospect prospect = new Prospect(UUID.randomUUID().toString(), name, totalLoan, yearlyInterest,
								years, E);
						// prospect.setResult(E);
						prospects.add(prospect);

					} else if (strLine.length() > 5) {
						String[] splitLine = strLine.split(",");
						String customerName = splitLine[0].replace("\"", "\\\"");
						totalLoan = Double.parseDouble(splitLine[1]);
						yearlyInterest = Double.parseDouble(splitLine[2]);
						years = Integer.parseInt(splitLine[3]);
						E = calculate(yearlyInterest, totalLoan, years);
						Prospect prospect = new Prospect(UUID.randomUUID().toString(), customerName, totalLoan,
								yearlyInterest, years, E);
						// prospect.setResult(E);
						prospects.add(prospect);
						System.out.println("Prospect :" + customerName + " wants to borrow " + totalLoan
								+ " € for a period of " + years + " years and pay " + E + " € each month");
					}
				}
				fstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!prospects.isEmpty()) {
			prospectRepository.saveAll(prospects);
		}
		return prospects.stream().map(prospect -> new ObjectMapper().convertValue(prospect, ProspectDTO.class))
				.collect(Collectors.toList());
	}

	/**
	 * @param b
	 * @param p
	 * @return
	 */
	static double pow(double b, int p) {
		if (p == 0) {
			return 1;
		}
		double result = b;
		for (int i = 1; i < p; i++) {
			result = b * result;
		}
		return result;
	}

	/**
	 * @param yearlyInterest
	 * @param totalLoan
	 * @param years
	 * @return
	 */
	public double calculate(double yearlyInterest, double totalLoan, int years) {
		double yearlyInterestRate;
		double monthlyInterest;
		int numberOfpayments;
		yearlyInterestRate = yearlyInterest / 100;
		monthlyInterest = (yearlyInterestRate) / 12;
		numberOfpayments = 12 * years;

		double v = 1 + monthlyInterest;
		double powResult = pow(v, numberOfpayments);

		double firstSeg = (monthlyInterest * powResult);
		double secSeg = (powResult - 1);

		double tempRes = firstSeg / secSeg;
		double res = totalLoan * tempRes;
		BigDecimal result = BigDecimal.valueOf(res);
		return result.setScale(2, RoundingMode.CEILING).doubleValue();
	}

	@Override
	public List<ProspectDTO> readAllProspect() {
		List<Prospect> prospects = prospectRepository.findAll();
		return prospects.stream().map(prospect -> new ObjectMapper().convertValue(prospect, ProspectDTO.class))
				.collect(Collectors.toList());
	}

}
