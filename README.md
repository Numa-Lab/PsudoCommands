# コマンドブロック対応版疑似セレクタープラグイン
- executeコマンドから**プラグインのコマンド**を実行できます。
- コマンドブロックも使用できます。

## スクリーンショット
![イメージ図](https://cdn.discordapp.com/attachments/611227726971404298/952238225769001080/unknown.png)  

## 使い方
### psudoコマンド
`psudo コマンド`
以下のようにセレクターを含めることができます  
セレクターはプレイヤー名に置き換えられます。　　
- `psudo say @a`  
- `execute run psudo say @a`  
- `execute as @a run psudo say @s`  
![psudo1](https://cdn.discordapp.com/attachments/611227726971404298/952240201307160686/unknown.png)  
![psudo2](https://cdn.discordapp.com/attachments/611227726971404298/952240878724984832/unknown.png)  
今回は分かりやすさのためsayを使用していますが、**セレクターに対応していないプラグインのコマンドを実行できます**。  
@aなど複数セレクターは人数分コマンドが繰り返し実行されます。(@aを2回入れた場合、人数^2乗回コマンドが実行されます)  

### psudoAsコマンド
`psudoAs コマンド`  
psudoと違い、そのプレイヤーとしてコマンドを実行します。  
対象のプレイヤーにコマンドの実行権限がない場合、失敗します。  
以下のようにセレクターを含めることができます  
- `psudoAs say @a`  
- `execute run psudoAs me @a`  
- `execute as @a run psudoAs me @s`  
![psudo1](https://cdn.discordapp.com/attachments/611227726971404298/952240992533225594/unknown.png)  
