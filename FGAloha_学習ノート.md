# FGAloha 学習ノート

> Java SE17 Gold学習 × アロハシャツECサイト開発で得た知識まとめ

---

## 目次

1. [application.properties](#1-applicationproperties)
2. [Springプロファイル分離](#2-springプロファイル分離)
3. [JPA・Hibernate基礎](#3-jpahibernate基礎)
4. [Entityの実装ルール](#4-entityの実装ルール)
5. [リレーションマッピング](#5-リレーションマッピング)
6. [Java Gold要素](#6-java-gold要素)
7. [Spring Security](#7-spring-security)
8. [AOP（アスペクト指向プログラミング）](#8-aopアスペクト指向プログラミング)
9. [Gitの基礎](#9-gitの基礎)
10. [次のタスク](#10-次のタスク)

---

## 1. application.properties

### 各設定の意味

```properties
# アプリ名（ログに [FGAloha] と表示される）
spring.application.name=FGAloha

# OracleのJDBCドライバーを指定
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# Oracle向けのSQL方言を指定（HibernateがSQL生成に使う）
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect

# テーブルの自動生成をオフ（DDLは自分で管理する）
spring.jpa.hibernate.ddl-auto=none

# 発行されたSQLをコンソールに表示
spring.jpa.show-sql=true

# テンプレートキャッシュをオフ（開発中は編集がすぐ反映される）
spring.thymeleaf.cache=false

# カスタムプロパティ（自由に定義できる）
ec.banner.message=¥10,000以上で送料無料
ec.free.shipping.threshold=10000
```

### カスタムプロパティの読み込み方

```java
// @Value で個別に読み込む
@Value("${ec.banner.message}")
private String bannerMessage;

// @ConfigurationProperties でクラスにまとめて読み込む（Gold推奨）
@ConfigurationProperties(prefix = "ec")
public class EcProperties {
    private String bannerMessage;
    private int freeShippingThreshold;
}
```

### `ddl-auto` の選択肢

| 値 | 意味 | 用途 |
|---|---|---|
| `none` | 何もしない | 本プロジェクト（DDL自己管理） |
| `create` | 起動時にテーブル作成 | 危険（本番データ消える） |
| `create-drop` | 起動時作成・終了時削除 | テスト用途のみ |
| `update` | 差分だけ変更 | 意図しない変更リスクあり |
| `validate` | Entity↔テーブルの整合性チェックのみ | 本番環境向け |

---

## 2. Springプロファイル分離

### なぜ分けるのか

パスワード等の秘密情報をGitHubに上げないため。

### ファイル構成

```
src/main/resources/
├── application.properties          → 共通設定（GitHubにcommit）
└── application-local.properties    → 秘密情報（.gitignoreで除外）
```

### `application-local.properties` の内容

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
spring.datasource.username=fgaloha
spring.datasource.password=実際のパスワード
```

### プロファイルの有効化

Eclipseの起動設定（VM引数）で指定する：
```
-Dspring.profiles.active=local
```

`application.properties` に `spring.profiles.active=local` を書いてはいけない！
→ GitHubにpushすると本番環境でも `local` プロファイルが強制されてしまう。

---

## 3. JPA・Hibernate基礎

### JPAとは

JavaオブジェクトとDBテーブルのマッピングを担う仕組み。
HibernateはJPAの実装ライブラリ。

### Hibernateが自動変換してくれること

```
Java世界                    DB世界
──────────────────          ──────────────────
ProductVariant              product_variants
  product: Product    ←→     product_id: NUMBER
  size: Size          ←→     size_id: NUMBER
  color: Color        ←→     color_id: NUMBER
```

### FetchType の違い

```java
// EAGER（@ManyToOneのデフォルト）
// 親Entityを取得した瞬間に関連Entityも一緒にSELECTされる
@ManyToOne  // → 即座に Product も取得

// LAZY（明示指定）
// 実際にgetXxx()を呼んだときに初めてSELECTされる
@ManyToOne(fetch = FetchType.LAZY)  // → 必要になってから取得
```

### N+1問題

```
EAGER で ProductVariant を100件取得
→ Product・Size・Color も自動で各100件取得
→ 合計400回のSELECTが走る 😱

LAZY にすることで必要なときだけSELECTが走る
```

---

## 4. Entityの実装ルール

### チェックリスト

```
□ @Entity
□ @Table(name = "テーブル名")
□ @Id
□ @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xxx_gen")
□ @SequenceGenerator(name = "xxx_gen", sequenceName = "xxx_seq", allocationSize = 1)
□ デフォルトコンストラクタ（JPAが必要）
□ 全引数コンストラクタ（リレーションは除く）
□ getter / setter
□ equals（パターンマッチング使用）
□ hashCode（equalsとセットで必ず実装）
□ toString（リレーションフィールドは含めない）
```

### equals / hashCode / toString をオーバーライドしない場合の問題

| メソッド | オーバーライドしないと |
|---|---|
| `toString` | デバッグ時にメモリアドレスしか表示されない |
| `equals` | 同じIDを持つEntityが別物と判断される |
| `hashCode` | HashSet・HashMapが正しく動かない |

### Java17 パターンマッチング（equals実装）

```java
// ✅ Java17の書き方
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Product product)) return false;
    return Objects.equals(id, product.id);
}

// 旧来の書き方
if (obj instanceof Product) {
    Product other = (Product) obj;  // キャストが別途必要
}
```

### @PrePersist（自動タイムスタンプ）

```java
@Column
private LocalDateTime createdAt;

@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now();
}
// setCreatedAt() は作らない（外から上書きできないようにする）
```

### NULL許容フィールドの型

```java
// NULL不可 → プリミティブ型
private int price;

// NULL許容 → ラッパークラス
private Integer freeThreshold;  // DBで DEFAULT NULL の場合

// ❌ EntityフィールドにOptionalは使わない
private Optional<Integer> freeThreshold;  // これはNG
```

### toString にリレーションを含めない理由

```
① 循環参照 → StackOverflowError
   Order.toString → OrderItem.toString → Order.toString → 無限ループ 💥

② LAZYロード → LazyInitializationException
   toString() でリレーションにアクセス
   → トランザクション外だとLAZYロードが失敗
```

---

## 5. リレーションマッピング

### getter / setter / コンストラクタ引数の判断表

| アノテーション | 側 | getter | setter | コンストラクタ引数 |
|---|---|---|---|---|
| `@ManyToOne` | オーナー | ✅ | ✅ | ❌ |
| `@OneToMany` | 非オーナー | ✅ | ❌ | ❌ |
| `@ManyToMany` | オーナー | ✅ | ✅ | ❌ |
| `@ManyToMany` | 非オーナー | ✅ | ❌ | ❌ |

**判断の一言まとめ**：オーナー側（外部キーを持つ側）はsetter✅、非オーナー側はsetter❌

### @ManyToOne（オーナー側・外部キーを持つ）

```java
// 外部キー（order_id）を持つ側
@ManyToOne(fetch = FetchType.LAZY)
private Order order;
```

### @OneToMany（非オーナー側・外部キーを参照）

```java
// 相手（OrderItem）のFKを通じて一覧を取得
@OneToMany(mappedBy = "order")  // OrderItemの"order"フィールド名
private List<OrderItem> orderItems = new ArrayList<>();
```

### @ManyToMany（中間テーブル）

```java
// オーナー側（Product）
@ManyToMany
@JoinTable(
    name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private List<Category> categories = new ArrayList<>();

// 非オーナー側（Category）
@ManyToMany(mappedBy = "categories")  // Productの"categories"フィールド名
private List<Product> products = new ArrayList<>();
```

### mappedBy の覚え方

```
mappedBy = 「相手クラスで自分を参照しているフィールド名」

Order側から見ると：
→ OrderItemが Orderを参照しているフィールド名
→ private Order order;
→ mappedBy = "order" ✅
```

### @ManyToMany で mappedBy を書く理由

書かないと中間テーブルへの INSERT が2重に走ってしまう。

### @Enumerated（Enumのマッピング）

```java
@Enumerated(EnumType.STRING)  // ← 必須！
@Column(name = "status")
private OrderStatus status = OrderStatus.PENDING;

// EnumType.ORDINAL（デフォルト）にすると 0, 1, 2... と数値で保存される
// → DBを直接見たとき意味が分からなくなる 😱
```

### @ManyToOne vs Enum の判断フロー

```
このカラムは別テーブルへのFKか？
        ↓
   YES → @ManyToOne
   NO  → 値の種類が固定か？
              ↓
         YES → Enum
         NO  → String / int / boolean 等
```

### スナップショット設計

注文時点の商品名・価格を String / int で保持する理由：

```
注文後に商品名や価格が変更されても
注文時点の情報が正確に残る

// OrderItem.java
private String productName;  // @ManyToOne ではなく String で持つ
private String sizeName;
private String colorName;
private int unitPrice;       // @ManyToOne ではなく int で持つ
```

---

## 6. Java Gold要素

### Stream API

```java
// variants（List）からStreamを生成
variants.stream()

// バラバラな値からStreamを生成
Stream.of(postalCode, prefecture, city, addressLine)
```

#### 中間操作

```java
.filter(v -> v.getStock() > 0)   // 条件で絞り込み
.map(ProductVariant::getColor)   // 別の型に変換（メソッド参照）
.distinct()                       // 重複除去
```

#### 終端操作

```java
.allMatch(s -> s != null && !s.isBlank())  // 全要素が条件を満たすか
.count()                                    // 件数カウント
.collect(Collectors.toList())              // Listに収集
```

#### 本プロジェクトでの使用例

```java
// isSoldOut()：全バリアントの在庫が0かどうか
public boolean isSoldOut() {
    return variants.stream()
        .allMatch(v -> v.getStock() == 0);
}

// getColorCount()：バリアントの色数をカウント
public long getColorCount() {
    return variants.stream()
        .map(ProductVariant::getColor)
        .distinct()
        .count();
}

// hasAddress()：住所が入力済みかどうか
public boolean hasAddress() {
    return Stream.of(postalCode, prefecture, city, addressLine)
        .allMatch(s -> s != null && !s.isBlank());
}
```

### Optional

```java
// ✅ メソッドの戻り値に使う
Optional<Product> findById(Long id);

// ✅ orElseThrow で例外を投げる
productRepository.findById(id)
    .orElseThrow(() -> new ProductNotFoundException(id));

// ❌ Entityのフィールドには使わない
private Optional<Integer> freeThreshold;  // NG
```

### String の null / 空チェック

```java
// isEmpty() → "" のみ true、"  " は false
// isBlank() → "" も "  " も true（Java11以降）

// 住所チェックなら isBlank() が適切
s != null && !s.isBlank()
```

### Enum（発展）

```java
public enum OrderStatus {
    PENDING("注文受付中"),
    SHIPPED("発送済み"),
    DELIVERED("配達完了"),
    CANCELLED("キャンセル");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
// Thymeleafで ${order.status.displayName} と書ける
```

---

## 7. Spring Security

### 仮実装（全許可・CSRF無効）

```java
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
```

### メソッドチェーンが使える理由

各メソッドが `HttpSecurity` を返すので連続して呼び出せる（Fluent Interface パターン）。

### STEP6での本設定イメージ

```java
http
    .csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/cart/**").authenticated()
        .anyRequest().permitAll()
    )
    .formLogin(login -> login
        .loginPage("/login")
        .defaultSuccessUrl("/")
    )
    .logout(logout -> logout
        .logoutSuccessUrl("/")
    );
```

---

## 8. AOP（アスペクト指向プログラミング）

### AOPとは

複数のクラスにまたがる横断的な関心事を一箇所に切り出す考え方。

### Springにおける実装例

```java
@Transactional    // トランザクション管理 → AOP
@PreAuthorize     // セキュリティ → AOP
@ControllerAdvice // 例外ハンドリング → AOP
```

### @ControllerAdvice による例外の一元管理

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound() {
        return "error/404";
    }

    @ExceptionHandler(OutOfStockException.class)
    public String handleOutOfStock() {
        return "error/stock";
    }
}
// 各Controllerはtry-catchを書かなくてよくなる
```

---

## 9. Gitの基礎

### 全体像

```
ローカル環境（自分のPC）        リモート環境（GitHub）
─────────────────────          ──────────────────
ワーキングツリー                リモートリポジトリ
（実際のファイル）
     ↓ git add
ステージングエリア
（コミット準備場所）
     ↓ git commit
ローカルリポジトリ
（.gitフォルダ）
     ↓ git push
                    ────────→ リモートリポジトリ
```

### 重要用語

| 用語 | 意味 |
|---|---|
| `repository` | 変更履歴を管理する場所 |
| `commit` | 変更をローカルリポジトリに記録する |
| `push` | ローカルの変更をリモートに送る |
| `pull` | リモートの変更をローカルに取り込む |
| `branch` | 開発の流れを分岐させる仕組み |
| `origin` | リモートリポジトリの別名（慣習） |
| `main` | デフォルトのブランチ名 |

### 初回セットアップコマンド

```cmd
git init
git add .
git commit -m "最初のコミット"
git branch -m master main
git remote add origin https://github.com/ユーザー名/リポジトリ名.git
git push -u origin main
```

### 2回目以降のpush

```cmd
git add .
git commit -m "コミットメッセージ"
git push
```

---

## 10. 次のタスク

```
⬜ 設計書の進捗更新（STEP1・STEP2を完了済みに）
⬜ STEP3：Repository層の実装
   ├── JpaRepository を継承するだけでCRUD完成
   ├── カスタムメソッドは @Query で追加
   └── 戻り値は Optional<T> を使う
```

---

*最終更新：STEP2（Entity層）完了時点*
