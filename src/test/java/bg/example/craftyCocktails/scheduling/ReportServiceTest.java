package bg.example.craftyCocktails.scheduling;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.example.craftyCocktails.model.entity.enums.SpiritNameEnum;
import bg.example.craftyCocktails.service.CocktailService;
import bg.example.craftyCocktails.service.EmailService;
import bg.example.craftyCocktails.service.UserService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

  @Mock
  private CocktailService cocktailService;
  @Mock
  private EmailService emailService;
  @Mock
  private UserService userService;
  @InjectMocks
  private ReportService reportService;

  @Test
  void testGenerateDailyReport() {
    LocalDate today = LocalDate.now();
    Instant todayMidnight = today.atStartOfDay(ZoneOffset.UTC).toInstant();

    when(cocktailService.findCountBySpirit(SpiritNameEnum.WHISKEY)).thenReturn(1L);
    when(cocktailService.findCountBySpirit(SpiritNameEnum.TEQUILA)).thenReturn(2L);
    when(cocktailService.findCountBySpirit(SpiritNameEnum.GIN)).thenReturn(3L);
    when(cocktailService.findCountBySpirit(SpiritNameEnum.RUM)).thenReturn(4L);
    when(cocktailService.findCountBySpirit(SpiritNameEnum.VODKA)).thenReturn(5L);
    when(cocktailService.findCountBySpirit(SpiritNameEnum.BRANDY)).thenReturn(6L);
    when(cocktailService.findCountBySpirit(SpiritNameEnum.NONE)).thenReturn(7L);
    when(cocktailService.findCountAll()).thenReturn(28L);

    List<String> adminsEmails = Arrays.asList("admin1@example.com", "admin2@example.com");
    when(userService.getAdminsEmails()).thenReturn(adminsEmails);

    reportService.generateDailyReport();

    String expectedReport = String.format("""
            Report on date: %s
                Whiskey cocktails: %d
                Tequila cocktails: %d
                Gin cocktails: %d
                Rum cocktails: %d
                Vodka cocktails: %d
                Brandy cocktails: %d
                Non-alcoholic cocktails: %d
                Cocktails all: %d
            """, todayMidnight,
        1, 2, 3, 4, 5, 6, 7, 28
    );

    verify(emailService).sendSimpleMessage("admin1@example.com",
        "This is autogenerated message from your application.",
        expectedReport
    );
    verify(emailService).sendSimpleMessage("admin2@example.com",
        "This is autogenerated message from your application.",
        expectedReport
    );
  }
}