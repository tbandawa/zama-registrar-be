package me.tbandawa.web.skyzmetro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportDto {
    List<MemberDto> members;
    String file;
}
