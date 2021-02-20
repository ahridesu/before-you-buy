# BeforeYouBuy 
De momento a app está funcionar para 2 exemplos

É possível adicionar e remover produtos dos favoritos.

É possível scannear um novo produto, sendo que ainda não é possível adicionar um novo produto à base de dados.

Interface de Utilizador a funcionar com um loading screen, home page e menus de favoritos e scanner.

Tem 2 exemplos funcionais, os codigos de barras encontram-se na pasta ImagensQRCode
# TODO
Criar menu de perfil do utilizador (perfil genérico neste momento, sendo mais tarde especifico a cada utilizador).

Modificar a base de dados para incluir a imagem do produto nos dados que são obtidos.

Criar getter para todos os produtos na base de dados neste momento só existe nos favoritos.

Pôr o menu de pesquisa de todos os produtos a funcionar corretamente (falta criar uma forma de filtrar produtos).

Criar um botão no menu scanner que quando um produto é scanneado pode levar a uma página que mostra os dados completos do produto.

Alterar o layout do scanner de forma a que a camara ocupe mais espaço do ecrã e apenas a parte inferior do ecrã fique disponível para as informações do produto.

Criar mais exemplos se necessário (minimo 3).

Criar uma barra para todos os menus que identifique a atividade (exceto scanner).

Criar forma de autenticação do utilizador.
# Como correr a App
Para correr a app primeiro tens de no teu telemóvel permitir a depuração por usb

Em caso de duvidas: https://docs.kony.com/konylibrary/visualizer/visualizer_user_guide/content/AndroidUSBDebugging_Windows10.htm#Enable

Depois importar o git para o android studio (já tem ferramenta dentro do android studio para fazer isso)

Na barra superior (ver link: https://prnt.sc/xzxjy7) deverá estar o teu telemóvel

Se estiver basta clicar no botão de play e após uns segundos a app deverá abrir no telemóvel se tudo estiver bem

# Testar o código
Há uma pasta chamada ImagensQRCode que tem as 2 imagens para os quais estão implementadas
