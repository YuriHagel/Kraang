package kraang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kraang.controller.dto.AvailableIdentifierDto;
import kraang.controller.dto.CreateIdentifierListDto;
import kraang.controller.dto.CreateSpecifiedIdentifierDto;
import kraang.controller.dto.common.ListResponse;
import kraang.controller.dto.common.Response;
import kraang.service.IdentifierBatchService;
import kraang.service.IdentifierService;
import kraang.service.dto.IdentifierDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/identifier", produces = APPLICATION_JSON_UTF8_VALUE)
@ApiResponses({@ApiResponse(code = 400, message = "Bad Request")})
@Api(tags = {"Identifiers"}, description = "Сервис номерной емкости")
public class IdentifierController {
  private final IdentifierService identifierService;
  private final IdentifierBatchService identifierBatchService;

  @ApiOperation("Загрузить указанный номер в Forward")
  @PostMapping("/create-specified-identifier")
  public Response<IdentifierDto>
  createSpecifiedIdentifier(@Valid @RequestBody CreateSpecifiedIdentifierDto createSpecifiedIdentifierRequest) {
    return new Response(identifierService.createSpecifiedIdentifier(createSpecifiedIdentifierRequest));
  }

  @ApiOperation("Загрузить номерную емкость в Forward")
  @PostMapping("/create-identifier-list")
  public ListResponse<IdentifierDto>
  createIdentifierList(@Valid @RequestBody CreateIdentifierListDto createIdentifierListRequest) {
    return new ListResponse(identifierBatchService.createIdentifiersBatch(createIdentifierListRequest));
  }

  @ApiOperation("Получить свободную номерную емкость")
  @GetMapping("/available-identifiers")
  public ListResponse<IdentifierDto>
  availableIdentifiers(@Valid AvailableIdentifierDto availableIdentifierDtoRequest) {
    return new ListResponse(identifierService.getAndLockAvailableIdentifier(availableIdentifierDtoRequest));
  }
}
