# exporter-lite
Export support in following format: (txt, csv, xls)
1. Text file  
2. CSV file 
3. Excel file 
 
#### Library Size : 527KB
  
## Setup 
Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
``` 
#### Dependency
[![](https://jitpack.io/v/appsfeature/exporter-lite.svg)](https://jitpack.io/#appsfeature/exporter-lite)
```gradle
dependencies {
    implementation 'com.github.appsfeature:exporter-lite:x.y'
}
```   

In your activity class:
#### Prepare List for this library
```java 
    public static List<List<String>> getExporterList() {
        List<User> yourList = new ArrayList<>();
        yourList.add(new User("01","Alpha"));
        yourList.add(new User("02","Bita"));

        //add header in your exporting list
        yourList.add(0, new User("Id","Name"));

        //make exporter list
        List<List<String>> exporterList = new ArrayList<>();
        for (User item : yourList){
            exporterList.add(Arrays.asList(item.getId(),item.getName()));
        }
        return exporterList;
    }
```
#### Get Sample List
```java 
    List<List<String>> exporterList = ExportSample.getExporterList(); 
```
In your activity class:
#### Export in Text file
```java 
    Exporter.getInstance().TextBuilder(this)
            .setFileName("SampleText")
            .setData(new ExporterData("Hello World!"))
            .setListener(new ExporterCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    ExporterShare.shareFile(MainActivity.this, result);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).export();
```
#### Export in CSV file
```java 
    Exporter.getInstance().CsvBuilder(this)
            .setFileName("SampleCsv")
            .setData(new ExporterData(body, false))
            .setListener(new ExporterCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    ExporterShare.shareFile(MainActivity.this, result);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).export();
```
#### Export in Excel file
```java 
    Exporter.getInstance().ExcelBuilder(this)
            .setFileName("SampleExcel")
            .setData(new ExporterData(body, true))
            .setListener(new ExporterCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    ExporterShare.shareFile(MainActivity.this, result);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).export();
```

 

For showing list of files. 
```java 
    Exporter.getInstance().showListOfFiles(this, new ExporterSelector<File>() {
        @Override
        public void onSelect(File result) {
            ExporterShare.shareFile(MainActivity.this, result);
        }
    });
```

For clear list of files.
```java
    Exporter.getInstance().clearListOfFiles(this);
```


## ChangeLog

#### Version 1.0:
* Initial build
* Library Size
* Supported format 'txt, csv, xls'
* compileSdkVersion 30
* Library : poi-3.17.jar
