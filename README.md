# CalabashKidVSMonsters
葫芦娃大战妖精游戏

## 资源文件



## 类设计

### 设计模式：

#### 单例模式

LocalGameController，线程安全

#### 工厂模式

CreatureFactory

BulletFactory

#### 建造者模式

TeamBuilder

#### 中介者模式

GameController

NetworkController

### MVC模式

BattlefieldController

Battlefield

MainWindowView

## 学会了什么

1. 设计模式
2. 网络设计
3. synchronized
4. Reentrant Lock
5. Consensus（有可能有子弹在我的客户端打到敌人，但是在敌人客户端因为延迟躲开了），所以用了简单的共识协议就是自己打到了敌人，敌人一定会被打到。