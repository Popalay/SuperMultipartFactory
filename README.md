[![Download](https://api.bintray.com/packages/popalay/maven/SuperMultipartFactory/images/download.svg) ](https://bintray.com/popalay/maven/SuperMultipartFactory/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache--2.0-green.svg)](https://github.com/Popalay/Tutors/blob/master/LICENSE)

# Super Multipart Factory

A simple way to show the user interface tutorials
* File support
* List support
* Nested model support

## Getting Started

```groovy
compile 'com.github.popalay:super-multipart-factory:latest-version'
```
## Usage

### From Java

```java
final NestedModel order = new NestedModel(21.34, "$");
final Model model = new Model(42, "Denis", "Nikiforov", "http://exaple.com/exaple.jpeg", order);

MultipartBody multipartBody = SuperMultipartFactory.create(model).getMultipartBody();

// Result:
// id = 42
// first_name = Denis
// last_name = Nikiforov
// avatar = http://exaple.com/exaple.jpeg
// order[amount] = 21.34
// order[currency_symbol] = $
```

See [sample](sample/src/main/java/com/github/popalay/sample/MainActivity.java).

License
-----

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
