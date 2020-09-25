# fuber

Backend APIs for on call cab service

## Installation

Download from https://github.com/Aman1994/fuber.

## Uberjar

```
lein uberjar
```

## Running the application

```
lein run
```
The above accepts the port number. If unspecified it will run at default port 3000

## Testing

```
lein test
```

### API Docs

1. For booking cabs -

```
Endpoint - /book-trip

Accepts  - It accepts params as query string. The params it accepts are `source` and `pink`. Source means your current location and `pink` means whether you want to book a pink cab or not. `source` must be a vector of latitute and longitude while pink must be a boolean.

Usage    - curl -i 'http://localhost:3000/book-trip?source=\[1,2\]&pink1=true'
```

2. For ending trips -

```
Endpoint - /end-trip

Accepts  - It accepts params as query string. The params it accepts are `license-num` and `destination`. `license-num` means the license number of the cab and `destination` means location you want to end the trip on. `license-num` must be string while `destination` must be a vector of latitute and longitude .

Usage    - curl -i 'http://localhost:3000/end-trip?license-num=432abc&destination=\[1,2\]'
```

3. Total amount earned by fuber -

```
Endpoint - /total-amount

Usage    - curl -i 'http://127.0.0.1:3000/total-amount'
```

4. Total available (free) cabs -

```
Endpoint - /available-cabs

Usage    - curl -i 'http://localhost:3000/available-cabs'
```

## License

Copyright © 2020 Aman Shah

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
