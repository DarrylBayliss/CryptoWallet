# CryptoWallet

CryptoWallet is an app to show a users ethereum balance and it's fiat value in US Dollars. On another screen, it also shows the users ERC-20 balance. Searchable via a list of top tokens.

# Tech Discussion

The app is architected using clean architecture as a rough base. The package structure is as follows:

- api
- di
- dto
- extensions
- models
- presentation
 - viewmodels
- repos

The dataflow of the app is as follows:

Fragment -> ViewModel -> Repo -> datasource

Note: datasource in this case is a network request, or in the case of the wallet address a hardcoded string.

The presentation components of the app are designed to be as flexible as possible. Making sure string resources are within strings.xml for localisation,
and using Fragments in case usecases like supporting tablets were added in the future.

Unit tests are added to cover the EthereumRepo. Mockito is used to mock the return values.
