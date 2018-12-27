# PdfToPng

特定のディレクトリ以下にあるPDFファイルを一括してPNGファイルにします。

```
Usage: PdfToPng
 --dpi N             : 解像度を指定します。 (default: 300)
 -d (--dir) VAL      : PDFがあるディレクトリを指定します。 (default: .)
 -f (--filename) VAL : PNG化したいファイルをひとつだけ指定します。
                       -d オプションと一緒に指定した場合こちらが優先されます。
 -h (--help)         : このヘルプを表示します。 (default: true)
 -r (--recursive)    : 再帰的に実行します。 (default: false)
```

## jarファイルの作成方法

```
./gradlew jar
```

として、 `build/libs` に得られるファイル `PdfToPng-*.jar` を利用してください。
