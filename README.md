# API.ai Plugin

## Goal

The goal is to transfer the user's request to a trained API.ai bot to continue dialog in the natural language. 

(!)Use case: interactive help. The user is prompted to chat with the bot to solve some problem.

## Use

The plugin is called via a link that is similar to "http://plugins.miniapps.run/ai_apiai?....". For instance:
```html
<page version="2.0">
  <div>
    Thank you for using our service!
  </div>
 
  <navigation>
    <link pageId="http://plugins.miniapps.run/ai_apiai?query=Hello&amp;back_url=index.xml&amp;token=23143434r54:er4hhigdsfsdafdadf">
      To first page
    </link>
  </navigation>
</page>
```

## Parameters
| Parameter     | Mandatory       | Value |
|:------------- |:---------------:|:-------------|
| query         | Yes             |The query text to be passed to API.ai bot when a user follows the link. For example, the text "Hi!" will cause the bot to greet the user in response. Or, it can be a question that the bot is supposed to answer.|
| back_url      | Yes             |Page address, to land the user to after talking with the bot. A return button will always be available to the user when talking with the bot.|
| token         | No              |API.ai bot developer's token. It is not mandatory, if parameter api-ai.token is specified in the service configuration.|


		
		
