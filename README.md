[![License](https://img.shields.io/github/license/project-openubl/xsender?logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)
![CI](https://github.com/project-openubl/xsender/workflows/CI/badge.svg)

[![Project Chat](https://img.shields.io/badge/zulip-join_chat-brightgreen.svg?style=for-the-badge&logo=zulip)](https://projectopenubl.zulipchat.com/)
[![Supported JVM Versions](https://img.shields.io/badge/JVM-11--17-brightgreen.svg?style=for-the-badge&logo=Java)](https://github.com/project-openubl/xsender/actions/runs/472762588/)

| Artifact                      | Version                                                                                                                                                                              |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| XBuilder                      | [![Maven Central](https://img.shields.io/maven-central/v/io.github.project-openubl/xbuilder)](https://search.maven.org/artifact/io.github.project-openubl/xbuilder/)                 |
| XBuilder Quarkus extension    | [![Maven Central](https://img.shields.io/maven-central/v/io.github.project-openubl/quarkus-xbuilder)](https://search.maven.org/artifact/io.github.project-openubl/quarkus-xbuilder/) |
| XSender                       | [![Maven Central](https://img.shields.io/maven-central/v/io.github.project-openubl/xsender)](https://search.maven.org/artifact/io.github.project-openubl/xsender/)                   |
| XSender Quarkus extension     | [![Maven Central](https://img.shields.io/maven-central/v/io.github.project-openubl/quarkus-xsender)](https://search.maven.org/artifact/io.github.project-openubl/quarkus-xsender/)   |
| XSender Spring Boot extension | [![Maven Central](https://img.shields.io/maven-central/v/io.github.project-openubl/spring-boot-xsender)](https://search.maven.org/artifact/io.github.project-openubl/spring-boot-xsender/)   |

# XBuilder

Librería Java para crear XMLs basados en UBL y los estándares de la SUNAT respecto a la facturación electrónica.

XBuilder esta diseñado para que puedas crear XMLs fácilmente.

- Crea XMLs sin que necesites conocer nada sobre manejo de archivos XMLs.
- Hace cálculos internos por ti.
- Requiere solamente datos mínimos.

## ¿Qué puedes hacer con XBuilder?

- Crear XMLs
- Firmar XMLs

### Update snapshots

```shell
mvn clean test -Dxbuilder.snapshot.update
```

# XSender

Libreria para realizar envíos de comprobantes electrónicos a los servicios web de la SUNAT y/o OSCE de acuerdo a lo
especificado por la SUNAT.

# Getting started

- [Documentación](https://project-openubl.github.io)
- [Discusiones](https://github.com/project-openubl/xsender/discussions)

## License

- [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)
