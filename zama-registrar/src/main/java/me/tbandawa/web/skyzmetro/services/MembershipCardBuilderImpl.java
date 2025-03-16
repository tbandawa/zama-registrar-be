package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.dtos.MemberDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipCardBuilderImpl implements MembershipCardBuilder {

    @Override
    public List<MemberDto> buildCards(List<MemberDto> memberDtoList) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Avengers");
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("IRON-MAN");
        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("SPIDER-MAN");

        return memberDtoList;
    }
}
