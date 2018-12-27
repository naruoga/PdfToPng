# PdfToPng

特定のディレクトリ以下にあるPDFファイルを一括してPNGファイルにします。

```
Usage: PdfToPng
 --dpi N          : 解像度を指定します。 (default: 300)
 -d (--dir) VAL   : PDFがあるディレクトリを指定します。省略した場合は実行したところです。 (default: .)
 -h (--help)      : このヘルプを表示します。 (default: true)
 -r (--recursive) : 再帰的に実行します。 (default: false)
```

## jarファイルの作成方法

```
./gradlew jar
```

として、 `build/libs` に得られるファイル `PdfToPng-*.jar` を利用してください。
