# ArgentWallet

ArgentWallet is an app to show a users ethereum balance and it's fiat value in US Dollars. On another screen, it also shows the users ERC-20 balance. Searchable via a list of top tokens.

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

# Questions

## How long did you spend on the exercise?

Approximately 15 hours.

## What would you improve if you had time?

I'm not satisfied with the formatting of the ethereum / US dollar value. I think it can be formatted better, I put this down to lack of knowledge on my part.

I would consider splitting up EthereumRepo into 3 repos. WalletRepo, TokenRepo and EthereumRepo. That way, wallet and token based methods are kept separate.

I'd like to adding a progress indicator to show when fields in the Fragment are being requested over the network.

On the ERC-20 screen. I would update it show the combined the value of all the ERC-20 contracts containing the token symbol the user searches for. At the moment, it returns the value of the first token.

Finally, add more unit tests to classes to make sure unit test coverage is kept up.

## What would you like to highlight in the code?

I'm quite proud of the SearchView on the ERC-20 Fragment. It uses follows Material Design conventions and puts the searchview in the menu. The top tokens are also shown as suggestions when the user begins to type.

I created my own dependency injection class called DependencyInjector for this project, due to the small amount of dependencies. If the project was to become bigger, I'd consider using Dagger or Hilt to help.

## If you had to store the private key associated to this ethereum account address, how would you make that storage secure.

I'd consider using the Android Keystore Provider. The benefits are the private key is only accessible through the app, and not available to the whole device. Keeping the private key secure.