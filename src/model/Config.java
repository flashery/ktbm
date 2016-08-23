package model;


public class Config {
  private String databaseName; 
  private String username;
  private String password;
  private String connectionUrl;
  
  public String getDatabaseName() {
    return databaseName;
  }
  
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getConnectionUrl()
  {
	  return this.connectionUrl;
  }
  public void setConnectionUrl(String connectionUrl)
  {
	  this.connectionUrl = connectionUrl;
  }
  @Override
  public String toString() {
    return "Item [Database Name = " + databaseName + ", Username = " + username + ", Password = " + password + "]";
  }
} 
