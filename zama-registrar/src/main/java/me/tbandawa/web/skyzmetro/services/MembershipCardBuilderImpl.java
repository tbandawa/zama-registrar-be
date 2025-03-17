package me.tbandawa.web.skyzmetro.services;

import lombok.extern.slf4j.Slf4j;
import me.tbandawa.web.skyzmetro.dtos.ExportDto;
import me.tbandawa.web.skyzmetro.dtos.MemberDto;
import me.tbandawa.web.skyzmetro.utils.ResourceType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
public class MembershipCardBuilderImpl implements MembershipCardBuilder {

    private final ImageService imageService;

    public MembershipCardBuilderImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public ExportDto buildCards(List<MemberDto> memberDtoList) {

        ExportDto exportDto = new ExportDto();

        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("zama_members");
            for (int i = 0; i < memberDtoList.size(); i++) {
                MemberDto member = memberDtoList.get(i);

                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(member.getNames() + " " + member.getSurname());
                row.createCell(1).setCellValue(member.getNationalIdNumber());
                row.createCell(2).setCellValue(member.getPosition());
                row.createCell(3).setCellValue(member.getDistrict());
                row.createCell(4).setCellValue(member.getMembershipNumber());

                File imageFile = imageService.getImageFile(member.getId(), ResourceType.ZAMA_MEMBER);
                InputStream imageInputStream = Files.newInputStream(imageFile.toPath());
                byte[] inputImageBytes = IOUtils.toByteArray(imageInputStream);

                int inputImagePicture = workbook.addPicture(inputImageBytes, Workbook.PICTURE_TYPE_PNG);

                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();

                XSSFClientAnchor imageAnchor = new XSSFClientAnchor();

                imageAnchor.setCol1(5);
                imageAnchor.setCol2(6);
                imageAnchor.setRow1(i);
                imageAnchor.setRow2(i + 1);

                drawing.createPicture(imageAnchor, inputImagePicture);

                sheet.autoSizeColumn(i);

            }

            FileOutputStream saveExcel = new FileOutputStream("files/exports/zama_members.xlsx");
            workbook.write(saveExcel);

            exportDto.setMembers(memberDtoList);
            exportDto.setFile("files/exports/zama_members.xlsx");

        } catch (Exception ex) {
            log.info(ex.getLocalizedMessage());
        }
        return exportDto;
    }
}
