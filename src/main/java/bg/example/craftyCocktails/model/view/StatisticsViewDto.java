package bg.example.craftyCocktails.model.view;

import java.time.LocalDateTime;

public class StatisticsViewDto {

  private long allCocktails;
  private long whiskeyCocktails;
  private long tequilaCocktails;
  private long ginCocktails;
  private long rumCocktails;
  private long vodkaCocktails;
  private long brandyCocktails;
  private long nonAlcoholicCocktails;
  private long usersCount;
  private LocalDateTime localDateTime;

  public long getAllCocktails() {
    return allCocktails;
  }

  public StatisticsViewDto setAllCocktails(long allCocktails) {
    this.allCocktails = allCocktails;
    return this;
  }

  public long getWhiskeyCocktails() {
    return whiskeyCocktails;
  }

  public StatisticsViewDto setWhiskeyCocktails(long whiskeyCocktails) {
    this.whiskeyCocktails = whiskeyCocktails;
    return this;
  }

  public long getTequilaCocktails() {
    return tequilaCocktails;
  }

  public StatisticsViewDto setTequilaCocktails(long tequilaCocktails) {
    this.tequilaCocktails = tequilaCocktails;
    return this;
  }

  public long getGinCocktails() {
    return ginCocktails;
  }

  public StatisticsViewDto setGinCocktails(long ginCocktails) {
    this.ginCocktails = ginCocktails;
    return this;
  }

  public long getRumCocktails() {
    return rumCocktails;
  }

  public long getVodkaCocktails() {
    return vodkaCocktails;
  }

  public long getBrandyCocktails() {
    return brandyCocktails;
  }


  public long getUsersCount() {
    return usersCount;
  }

  public StatisticsViewDto setUsersCount(long usersCount) {
    this.usersCount = usersCount;
    return this;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public StatisticsViewDto setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
    return this;
  }

  public StatisticsViewDto setRumCocktails(long rumCocktails) {
    this.rumCocktails = rumCocktails;
    return this;
  }

  public StatisticsViewDto setVodkaCocktails(long vodkaCocktails) {
    this.vodkaCocktails = vodkaCocktails;
    return this;
  }

  public StatisticsViewDto setBrandyCocktails(long brandyCocktails) {
    this.brandyCocktails = brandyCocktails;
    return this;
  }

  public long getNonAlcoholicCocktails() {
    return nonAlcoholicCocktails;
  }

  public StatisticsViewDto setNonAlcoholicCocktails(long nonAlcoholicCocktails) {
    this.nonAlcoholicCocktails = nonAlcoholicCocktails;
    return this;
  }
}
