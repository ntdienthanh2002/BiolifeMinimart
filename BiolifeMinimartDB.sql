create database BiolifeMinimart
use BiolifeMinimart

create table Employee (EmployeeUsername varchar(50) primary key,
						EmployeeFullname varchar(100) not null,
						EmployeeGender int not null,
						EmployeeEmail varchar(70) not null,
						EmployeeAddress varchar(255) null,
						EmployeePassword varchar(50) not null,
						EmployeePhone varchar(20) not null,
						EmployeeAvatar varchar(max) not null,
						EmployeeStatus int not null)
						
create table Customer (CustomerUsername varchar(50) primary key,
						CustomerFullname varchar(100) not null,
						CustomerGender int not null,
						CustomerEmail varchar(70) not null,
						CustomerPassword varchar(50) not null,
						CustomerPhone varchar(20) not null,
						CustomerAvatar varchar(max) not null,
						CustomerStatus int not null)

create table Receiver (ReceiverID int primary key identity(1, 1),
						CustomerUsername varchar(50) foreign key references Customer(CustomerUsername) not null,
						ReceiverName varchar(100) not null,
						ReceiverPhone varchar(20) not null,
						ReceiverAddress varchar(255) not null,
						ReceiverStatus int not null)

create table Category (CategoryID int primary key identity(1, 1),
						CategoryName varchar(50) not null,
						CategoryImage varchar(max) not null,
						CategoryStatus int not null)

create table Supplier (SupplierID int primary key identity(1, 1),
						SupplierName varchar(100) not null,
						SupplierAddress varchar(255) not null,
						SupplierPhone varchar(20) not null,
						SupplierEmail varchar(70) not null,
						SupplierStatus int not null)

create table Product (ProductID int primary key identity(1, 1),
						CategoryID int foreign key references Category(CategoryID) not null,
						SupplierID int foreign key references Supplier(SupplierID) not null,
						ProductName varchar(50) not null,
						ProductQuantity int not null,
						ProductImportPrice money not null,
						ProductUnitPrice money not null,
						ProductQuantityPerUnit varchar(20) not null,
						ProductDescription varchar(max) not null,
						ProductImage varchar(max) not null,
						ProductStatus int not null)

create table ProductImage (ProductImageID int primary key identity(1, 1),
							ProductID int foreign key references Product(ProductID) not null,
							ProductImagePath varchar(max) not null)

create table Promotion (PromotionID int primary key identity(1, 1),
						PromotionName varchar(100) not null,
						PromotionStart date not null,
						PromotionEnd date not null)

create table PromotionDetails (PromotionDetailsID int primary key identity(1, 1),
								PromotionID int foreign key references Promotion(PromotionID) not null,
								ProductID int foreign key references Product(ProductID) not null,
								PromotionDetailsDiscount float not null)

create table Orders (OrderID int primary key identity(1, 1),
						ReceiverID int foreign key references Receiver(ReceiverID) not null,
						OrderDate datetime not null,
						OrderDeliveryDate datetime null,
						OrderTotal money not null,
						OrderNote varchar(255) null,
						OrderStatus int not null)

create table OrderDetails (OrderDetailsID int primary key identity(1, 1),
							OrderID int foreign key references Orders(OrderID) not null,
							ProductID int foreign key references Product(ProductID) not null,
							OrderDetailsQuantity int not null,
							OrderDetailsUnitPrice money not null,
							OrderDetailsDiscount float not null)

create table Feedback (FeedbackID int primary key identity(1, 1),
						CustomerUsername varchar(50) foreign key references Customer(CustomerUsername) not null,
						OrderID int foreign key references Orders(OrderID) null,
						ProductID int foreign key references Product(ProductID) null,
						FeedbackDate date not null,
						FeedbackContent varchar(max) not null,
						FeedbackRate int not null,
						FeedbackStatus int not null)

create table Cart (CartID int primary key identity(1, 1),
					CustomerUsername varchar(50) foreign key references Customer(CustomerUsername) not null,
					ProductID int foreign key references Product(ProductID) not null,
					CartQuantity int not null)

insert into Employee values ('Owner', 'Dien Thanh', 1, 'ntdienthanh1011@gmail.com', '40 Nguyen Van Linh - Ninh Kieu - Can Tho', '4381A72F77CC5E0002808A6E9267A90C', '0706991366', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680096409/Biolife/Employee/Employee_default.jpg', 0),
							('Employee1', 'Truc Anh', 1, 'dttrucanh1709@gmail.com', '50 Mau Than - Ninh Kieu - Can Tho', '6D226B9F9347E1568CB898377FA19CEA', '0123456123', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680096409/Biolife/Employee/Employee_default.jpg', 1),
							('Employee2', 'Duy Khanh', 0, 'voduykhanh3112@gmail.com', '60 Ly Tu Trong - Ninh Kieu - Can Tho', '6D226B9F9347E1568CB898377FA19CEA', '0888999888', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680096409/Biolife/Employee/Employee_default.jpg', 2)

insert into Customer values ('NTDThanh', 'Dien Thanh', 1, 'ntdienthanh1011@gmail.com', 'A421E6B1F4EF36EE345DB8DB566D6B02', '0706991366', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679917899/Biolife/Customer/customer_default.jpg', 1),
							('DTTAnh', 'Truc Anh', 1, 'dttrucanh1709@gmail.com', 'A421E6B1F4EF36EE345DB8DB566D6B02', '0123456123', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679917899/Biolife/Customer/customer_default.jpg', 1),
							('VDKhanh', 'Duy Khanh', 0, 'voduykhanh3112@gmail.com', 'A421E6B1F4EF36EE345DB8DB566D6B02', '0888999888', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679917899/Biolife/Customer/customer_default.jpg', 1)

insert into Receiver values ('NTDThanh', 'Dien Thanh', '0706991366', '40 Nguyen Van Linh - Ninh Kieu - Can Tho', 1),
							('NTDThanh', 'Dien Thanh', '0706991365', '41 Chau Van Liem - Ninh Kieu - Can Tho', 2),
							('NTDThanh', 'Dien Thanh', '0706991364', '42 Nam Ky Khoi Nghia - Ninh Kieu - Can Tho', 2)

insert into Category values ('Drink Fruits', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682185164/Biolife/Category/Category1.jpg', 1),
							('Dry Fruits', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680157960/Biolife/Category/Category2.jpg', 1),
							('Fresh Fruits', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682058350/Biolife/Category/Category3.jpg', 1),
							('Pulses', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679920739/Biolife/Category/Category4.jpg', 1),
							('Vegetables', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679920703/Biolife/Category/Category5.jpg', 1)

insert into Supplier values ('Klever Fruit', '06 Ly Thuong Kiet - Hoan Kiem - Ha Noi', '0969817915', 'cskh@kleverjuice.com.vn', 1),
							('FS', '174 Dien Bien Phu - Binh Thanh - Ho Chi Minh City', '0985555654', 'fstraicaytuoi@gmail.com', 1),
							('Vinamit', '81 Nguyen Du - Ben Cat - Binh Duong', '0903931961', 'contact@vinamit.com.vn', 1),
							('Organica', '6A Ly Dao Thanh - Hoan Kiem - Ha Noi', '0898991535', 'organica@organica.vn', 1),
							('NongLamFood', '44 Nguyen Trai - Thanh Xuan - Ha Noi', '0987104511', 'cskh@nonglamfood.com', 1)

insert into Product values (3, 1, 'Blueberries Organic', 72, 7, 7.99, 'kg', '<p>Blueberries are a hybrid of flavours having a slight sweet taste with a bit of acid. They are bursting with flavour and have a juicy mouthfeel. A good source of antioxidants, with some Vitamin A and C. Should be stored in a covered container in refrigerator for up to a week.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679924511/Biolife/Product/Product1.jpg', 1),
						   (3, 2, 'Lemons Organic', 86, 10, 10.49, 'kg', '<p>The juice has a distinctive sour taste. Lemons are a key ingredient in baked goods, sauces, salad dressings, marinades, drinks, and desserts. They are also perfect to make lemonade. They provide a good source of vitamin C, B6 and dietary fibre.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679924525/Biolife/Product/Product2.jpg', 1),
					   	   (3, 1, 'Strawberries Large', 68, 35, 35.5, 'kg', '<p>Strawberries are soft, sweet, bright red berries. They are a delicious quick snack or can be used in jams, desserts and cocktails. They are a very good source of vitamin C and a good source of folic acid and dietary fibre.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1679924534/Biolife/Product/Product3.jpg', 1),
						   (3, 4, 'Raspberries', 59, 50, 50.5, 'kg', '<p>Raspberries are bright red, with a hollow core and round shape. They have a fresh sweetness to them, but their tartness undertone makes their sweetness subtler than a strawberry. They also have a floral note which helps sedate the sweetness. They are delicious when you eat them straight or toss them into oatmeal, salads, yoghurt or smoothies. They are good source of dietary fibre and vitamin C with some vitamins A, B, B2, calcium, phosphorus magnesium and iron.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015301/Biolife/Product/Product4.jpg', 1),
						   (3, 1, 'Cherries Premium', 83, 33, 33.69, 'kg', '<p>Round, small and red. Cherries tend to have a combination of sweet and sour according to the species. A good source of vitamin C and a useful source of potassium and dietary fibre. Can be kept refrigerated for up to a week and should be washed before consumption.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015394/Biolife/Product/Product5.jpg', 1),
						   (5, 4, 'Tomatoes Roma', 48, 13, 13.49, 'kg', '<p>Roma tomatoes are a red and firmer-skinned tomato with a sweet, juicy flesh, used both fresh and for processing. They tend to be oblong in shape (hence the nickname egg tomatoes) and heavy for their size. Usually they are dearer than gourmets. They are highly nutritious, sweet and delicious due to their natural sugars content, sucrose and fructose. They are a good source of vitamin C and supply some vitamin E, folate and dietary fibre.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015848/Biolife/Product/Product6.jpg', 1),
						   (5, 3, 'Carrots Organic', 67, 6, 6.99, 'kg', '<p>The taste of carrots is a unique composition between sweet, fruity and more harsh or bitter flavours. Carrots can be eaten raw or steamed, roasted, baked and even juiced. They go well with butter, beef, chicken, cumin, feta and honey. They are an excellent source of vitamin A, good source of dietary fibre. It utilises organic farming to ensure production involves much more than not using chemical pesticides and fertilisers. This helps to minimise any negative impacts on the environment, maintaining biodiversity of the ecosystem whilst also delivering a nutritious product.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015917/Biolife/Product/Product7.jpg', 1),
						   (5, 3, 'Cauliflower Organic', 99, 9, 9.29, 'kg', '<p>Cauliflower has a sweet and nutty flavour with bitter undertones. Its flavour is mild and texture quite crunchy making it perfect for a variety of recipes. Chuck it in a soup, veggie-bake or make cauliflower rice. It&#39;s a good source of vitamin C, potassium, folic acid. It utilises organic farming to ensure production involves much more than not using chemical pesticides and fertilisers. This helps to minimise any negative impacts on the environment, maintaining biodiversity of the ecosystem whilst also delivering a nutritious product.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015981/Biolife/Product/Product8.jpg', 1),
						   (5, 4, 'Capsicums Red', 78, 14, 14.69, 'kg', '<p>These are more mature than green, orange or yellow capsicums. They are rich in carotenoid phytonutrients and contain almost eleven times more beta-carotene than green capsicums as well as one and a half times more vitamin C. Red capsicums have a sweet, almost fruity taste. Their bright colour and sweet flavour make them perfect for tossing into salads. When roasted red capsicums become silken and luscious. Pimento and paprika are both prepared from red capsicums. Excellent source vitamin C, good source B6, E and potassium.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016079/Biolife/Product/Product9.jpg', 1),
						   (5, 3, 'Pumpkin Kent Organic', 55, 10, 10.58, 'kg', '<h3><strong>Baccis - Fresh Pasta - Ravioli Pumpkin</strong></h3><p><strong>Contains Allergen</strong></p><p>Egg, Gluten, Dairy</p><p><strong>Ingredients</strong></p><p>Durum Wheat Semolina, Water, Pumpkin (10%), Ricotta / Parmesan Cheese (Contains Dairy), Breadcrumbs (Wheat Flour, Wholemeal Flour, Maize Flour, Onion, Yeast, Salt, Vinegar, Canola Oil, Preservative (282), Colours (102, 122), (Thiamine, Folate), Whole Egg, Vegetable Oil, Gluten, Mushroom Extract (Dextrose, Maltodextrin, Natural Vegetable Flavors, Vegetable Protein Extract, Dehydrated Vegetables (Onion, Garlic), Yeast Extract, Flavor Enhancers (627, 631), Caramel Color (150a), Black Pepper), Salt, White Pepper.</p><p><strong>Summary</strong></p><p>Baccis Fresh Pasta Pumpkin Ravioli Gourmet 500g</p><p><strong>Preparation</strong></p><p>Gently pour pasta into 4 litres of steady boiling water for 12 to 15 minutes, salt to taste. Add your favourite sauce.</p><p><strong>Storage</strong></p><p>Keep Refrigerated Below 4C.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016140/Biolife/Product/Product10.jpg', 1),
						   (1, 2, 'BICKFORDS Cranberry Juice Drink', 80, 5, 5.69, 'bottle', '<p>Cranberry juice has risen in popularity, not only for its well documented health benefits, but also for its versatility in cocktails and cooking. This extremely popular fruit juice now has a firm place at the table, on both ends of the day!</p><p>Made with the absolute finest quality Cranberries, Bickfords Cranberry Juice Drink a mouth-watering juice drink made to deliver natural sweetness with a satisfying, tart finish. Renowned for their high antioxidant levels as well as Vitamin C, cranberries have a range of other nutritional benefits that may add to a healthy lifestyle. It is no secret that cranberries have been used for centuries as a medicinal aid due to their high nutrient combination. Bickfords Cranberry Juice Drink is beautifully versatile and a chilled source of goodness.</p><p>It is Australian made and has no artificial flavours, colours or preservatives. It is only natural.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015504/Biolife/Product/Product11.jpg', 1),
						   (1, 2, 'Bite Riot - Juice Pure Cherry (1L)', 76, 8, 8.49, 'bottle', '<h3><strong>Bite Riot - Juice Pure Cherry (1L)</strong></h3><ul><li>BiteRiot! Cherry Juice has 17 times more antioxidants than red grapes!</li><li>It contains freshly cold-pressed cherries, absolutely nothing else.</li><li>Packed with anti-oxidants naturally high in Vitamin C, potassium.</li><li>no added sugar, no added water, no added flavour</li><li>preservative free</li><li>not made from concentrate</li></ul><p>Made from hand-picked, mountain-grown, sweet cherries from the central west of NSW Australia. The flavour of BiteRiot! is captured in a unique pasteurisation process that gives BiteRiot! Cherry Juice a two-year shelf life (unopened) at room temperature.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015580/Biolife/Product/Product12.jpg', 1),
						   (1, 4, 'Cocosoul Organic Coconut Water', 60, 7, 7.49, 'bottle', '<h3><strong>Cocosoul Organic Coconut Water</strong></h3><p>Rich in electrolytes</p><p>Cocosoul tender organic coconut water has a better composition of minerals like calcium, iron, maganese, magnesium and zinc than some fruit juices.</p><p>Cocosoul coconuts are carefully selected to meet our exacting standards. The coconuts that they select contain 200-1000ml of water. They also look for coconuts that are a certain age. Too young and they will be bitter and devoid of nutrients. Mature coconuts contain less water and have thicker meat.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015619/Biolife/Product/Product13.jpg', 1),
						   (1, 2, 'DEWLANDS Pineapple Juice 1L', 104, 3, 3.29, 'bottle', '<h3><strong>DEWLANDS Pineapple Juice 1L</strong></h3><p>No Added Sugars Or Preservatives</p><h3><strong>Description</strong></h3><p>From the heart of the Cape fruit orchards we bring you Dewlands Fruit Juices, a premium product that is skilfully blended using only the finest quality fruit juice ingredients. Experience the authentic taste and bouquet of Pineapple and Grape Juice. - Fruit Juice Blend - With Added Vitamin C - No Added Sugar - Preservative Free</p><h3><strong>Ingredients</strong></h3><p>Ingredients Made from Concentrate. Pineapple Juice (54%), Grape Juice (46%), Vitamin C</p><h3><strong>Summary</strong></h3><p>Fruit Juices. The taste of pineapple juice. Pineapple and Grape fruit juice blend. No added sugars or preservatives. Blended with grape juice.</p><h3><strong>Preparation</strong></h3><p>Shake well</p><h3><strong>Storage</strong></h3><p>Keep refrigerated once opened</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015685/Biolife/Product/Product14.jpg', 1),
						   (1, 4, 'BICKFORDS Prune Juice', 88, 6, 6.29, 'bottle', '<h3><strong>BICKFORDS PRUNE JUICE</strong></h3><p>If you need a little bit of help getting your digestive health moving in the right direction, Bickfords 100% Prune Juice could become your new best friend! Prunes and Prune Juice are globally renowned as an aid to digestive health due to their high sorbitol content and are believed to have many other benefits which can assist in promoting your overall wellbeing.</p><p>Prunes are extremely high in potassium which your body does not produce naturally, so a daily dose of Bickfords Premium Prune juice is an easy way to help supplement a balanced diet.</p><p>Made from 100% prune juice, this all-natural juice has pleasurably warm flavour and balanced, full-bodied mouth feel with no aftertaste. It really is goodness that tastes great.</p><p>Bickfords 100% Premium Prune Juice is low in sodium and has no artificial flavours, colours or preservatives.</p><p>It is Australian Made and contains no added sugar. It is only natural.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680015777/Biolife/Product/Product15.jpg', 1),
						   (2, 5, 'Mareeba Orchards Australian Dried Mango', 77, 110, 117.5, 'pack', '<p>There delicious fruit is grown in Far North Queensland. It is dehydrated and packed on there farm for maximum freshness and taste.<br /><br />Far North Queensland is renowned for its fertile soil and tropical fruit farms.<br /><br />Mareeba Orchards Australian dried bananas are eco-organically grown and minimally processed to ensure maximum nutrition and flavour.</p><p><strong>Ingredients: 100% Australian grown mango.</strong></p><ul><li><p>Rich in antioxidants</p></li><li><p>No preservatives</p></li><li><p>No added sugar</p></li><li><p>Kosher</p></li><li><p>Vegan friendly</p></li><li><p>Nutritious snack for the whole family</p></li></ul><ul></ul><p>Nutritious snack for the whole family</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016269/Biolife/Product/Product16.jpg', 1),
						   (2, 5, 'Roy Farms - Dried Mixed Fruit', 52, 62, 62.45, 'pack', '<h3><strong>Roy Farms - Dried Mixed Fruit</strong></h3><p><strong>Australian Mixed Fruit</strong></p><p>Roy Farms Mixed Fruits contains a selection of fruits that are tree ripened and when at their optimal ripeness they are hand picked, cut and preserved.</p><p>All the fruits are sun dried on the lawns at Renmark, South Australia following a 170 year old family tradition. The Mixed Fruit is a delicious moist and sweet fruit selection and a great way to sample a wide range of fruits to see what you might like best.</p><p><strong>Allergens and Advice</strong></p>p>Product contains Sulphites.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016278/Biolife/Product/Product17.jpg', 1),
						   (2, 2, 'Roy Farms - Dried Pears', 125, 50, 52.45, 'pack', '<h3><strong>Roy Farms - Dried Pears</strong></h3><p><strong>Australian Dried Apricots</strong></p><p>Roy Farms Apricots are tree ripened and when at their optimal ripeness they are hand picked, cut and preserved.</p><p>The Apricots are then sun dried on the lawns at Renmark, South Australia following a 170 year old family tradition the results speak for themselves, delicious moist and sweet Apricots.</p><p><strong>Allergens and Advice</strong></p><p>Product contains Sulphur.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016278/Biolife/Product/Product18.jpg', 1),
						   (2, 5, 'Roy Farms - Dried Nectarines', 74, 50, 57.45, 'pack', '<h3><strong>Roy Farms - Dried Nectarines</strong></h3><p><strong>Australian Dried Yellow Nectarine</strong></p><p>Roy Farms yellow and white Nectarines are tree ripened and when at their optimal ripeness they are hand picked, cut and preserved.</p><p>The Nectarines are then sun dried on the lawns at Renmark, South Australia following a 170 year old family tradition the results speak for themselves, delicious moist and sweet Nectarines.</p><p><strong>Allergens and Advice</strong></p><p>Product contains Sulphites.</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016277/Biolife/Product/Product19.jpg', 1),
						   (2, 2, 'Mareeba Orchards Australian Dried Banana', 98, 55, 58.7, 'pack', '<h3><strong>Mareeba Orchards Australian Dried Banana</strong></h3><p>There delicious fruit is grown in Far North Queensland. It is dehydrated and packed on there farm for maximum freshness and taste.<br /><br />Far North Queensland is renowned for its fertile soil and tropical fruit farms.<br /><br />Mareeba Orchards Australian dried bananas are eco-organically grown and minimally processed to ensure maximum nutrition and flavour.<br /><br />Ingredients: 100% Australian cavendish bananas.</p><ul><li>High in Vitamin B6</li><li>Ecoganic bananas grown with no insecticides, nematicides or meticides</li><li>No preservatives</li><li>No added sugar</li><li>Kosher Dairy (Certified by Kashrut Authority)</li><li>Vegan Friendly</li></ul><p>Nutritious snack for the whole family</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016262/Biolife/Product/Product20.jpg', 1),
						   (4, 3, 'Belladotti -  Seaweed and Soy - Super Salad Seeds', 97, 40, 44.1, 'pack', '<h3><strong>Belladotti - Salad Toppers - Seaweed and Soy - Super Salad Seeds</strong></h3><p>Salad Toppers are a quick and easy way to add crunch and flavour to your salads!</p><p>For added nutrition try our dry roasted sunflower sesame seeds, pepitas, poppy chia seeds, all tossed in Soy Sauce and mixed with Nori Seaweed. Good source of protein, fibre, omega 3 fatty acids, magnesium zinc (for cardiovascular health immune support).</p><p>* Nutritions<br />* Dairy Free<br />* All natural<br />* Vegan friendly</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680016833/Biolife/Product/Product21.jpg', 1),
						   (4, 5, 'Brookfarm Nuts Snack Explorer Brothers Blend', 89, 75, 80, 'pack', '<h3><strong>Brookfarm Nuts Snack Explorer Brothers Blend</strong></h3><ul><li>Oven roasted premium nuts</li><li>Tart cherries and cranberries</li><li>Delicious coconut</li><li>Preservative and gluten free</li></ul><p>Explore. Adventure. Voyage you will want more<br /><br />This premium blend, recently awarded a Silver Medal at the Royal Hobart Fine Food awards, is a reflection of the search Will and Eddie Brook have travelled, to find the worlds finest ingredients.<br /><br />The combination of roasted Australian almonds, pecans, walnuts, tart sour cherries, cranberries and roasted coconut makes this mix unbelievably delicious.<br /><br />Whether it is sharing time with family and friends or packing for your next adventure, this new mix from the Brook brothers is perfectly blended. Our Brothers Blend Explorer mix will take you to those special places, anytime, anywhere.<br /><br />Life is a journey Explore your own world.</p><p><strong>Ingredients:</strong> Nuts (40%) (pecans, almonds, walnuts, hazelnuts), Fruit (30%) [flame raisins, tart cherries (tart cherries, sugar, sunflower oil), cranberries (cranberries, sugar, sunflower oil)], coconut (10%), pumpkin kernels, sunflower kernels, macadamia oil, pink lake salt.<br /><br /><strong>Allergens:</strong><br />Tree Nuts</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680017000/Biolife/Product/Product22.jpg', 1),
						   (4, 3, 'Chefs Choice Organic Chia Seed', 90, 19, 21, 'pack', '<h3><strong>Chefs Choice Organic Chia Seed</strong></h3><p>Chefs Choice Certified Organic Chia Seed is one of nature super foods that are high in beneficial nutrients. They are easy to use and can become a valuable addition into your diet. They can be used in any of your recipes, perfect in muesli, be sprinkled onto a salad, or be added into a smoothie!</p><p><strong>Ingredients:</strong> Organic Chia Seeds (100%).<br /><br /> - No artificial flavours<br /> - No artificial colours<br /> - No preservatives<br /> - GMO free<br /> - 100% natural<br /> - Vegan friendly<br /> - Antioxidants<br /> - Good source of dietary fibre<br /> - Source of Protein<br /> - Kosher (Certified by Kashrut Authority)<br /><br /><strong>May contain traces of SOY and GLUTEN.</strong><br /><br /><strong>Nutrition Information</strong><br />Servings per package: 30g Serving size: 16<br />(Average Quantity per Serve)<br />Energy (kJ) 560 kJ<br />Protein 5.9 g<br />Fat, total 10.2 g<br /> - saturated 1.1 g<br />Carbohydrate 11.6 g<br /> - sugars 0 g<br />Dietary fibre 6.9 g<br />Sodium (mg) 3 mg<br /><br />(Average Quantity per 100g)<br />Energy (kJ) 1864 kJ<br />Protein 19.5 g<br />Fat, total 34.0 g<br /> - saturated 3.8 g<br />Carbohydrate 38.5 g<br /> - sugars 0 g<br />Dietary fibre 23.0 g<br />Sodium (mg) 9 mg</p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680017113/Biolife/Product/Product23.jpg', 1),
						   (4, 3, 'HARRIS FARM Almonds Raw 250G', 80, 20, 25.16, 'pack', '<h3>HARRIS FARM Almonds Raw</h3><p>These almonds have been long-soaked and then long-dried at temperatures less than 70C, ensuring activation, preservation of nutrients, and naturally occurring, beneficial microorganisms.</p><p><strong>Ingredients:</strong> Organic Almonds, Filtered Water, and Raw Australian sea salt.</p><p>Made on the same equipment as products containing whey and other nuts. May contain a shell.</p><p><strong>Nutrition Information:</strong></p><p>Servings per Package: 4 - Serving Size: 30g</p><table style="width:100%"><tbody><tr><td></td><td>Avg Qty per Serving</td><td>Avg Qty per 100g</td></tr><tr><td><p>Energy</p><p>Protein</p><p>Fat, Total</p><p>Saturated</p><p>Carbohydrate</p><p>Sugars</p><p>Sodium</p></td><td><p>769 kJ</p><p>6.0g</p><p>14.3g</p><p>0.3g</p><p>8.0g</p><p>1.3g</p><p>28 mg</p></td><td><p>2563 kJ</p><p>20g</p><p>47.8g</p><p>0.9g</p><p>26.7g</p><p>4.3g</p><p>92mg</p></td></tr></tbody></table><p><em>Disclaimer: Product and Nutrition Information may change from time to time. Please refer to the actual pack label for the most accurate data before consumption.</em></p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680017157/Biolife/Product/Product24.jpg', 1),
						   (4, 5, 'Mount Zero Bio - Dynamic Soup Mix', 79, 10, 14.98, 'pack', '<h3><strong>Mount Zero Bio - Dynamic Soup Mix</strong></h3><p>There soup mix is a roll - call of the best biodynamic produce from the Wimmera, with French Style green lentils, pearl barley farro offering great flavour and texture, plus split red lentils and split yellow peas to work as a thickener and binder of flavours.<br /><br />No need to soak, simply add to soups, stews and casseroles. An excellent source of protein, dietary fibre and carbohydrates.<br /><br /><strong>Contains gluten.</strong></p>', 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680017206/Biolife/Product/Product25.jpg', 1)						

insert into ProductImage values (1, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680150715/Biolife/ProductImage/Product1_1.jpg'),
								(1, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680150716/Biolife/ProductImage/Product1_2.jpg'),
								(1, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680150719/Biolife/ProductImage/Product1_3.jpg'),
								(1, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1680150721/Biolife/ProductImage/Product1_4.jpg'),
								(2, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086518/Biolife/ProductImage/Product2_5.jpg'),
								(2, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086521/Biolife/ProductImage/Product2_6.jpg'),
								(2, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086523/Biolife/ProductImage/Product2_7.jpg'),
								(2, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086527/Biolife/ProductImage/Product2_8.jpg'),
								(3, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086781/Biolife/ProductImage/Product3_9.jpg'),
								(3, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086785/Biolife/ProductImage/Product3_10.jpg'),
								(4, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086865/Biolife/ProductImage/Product4_11.jpg'),
								(4, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086867/Biolife/ProductImage/Product4_12.jpg'),
								(5, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086966/Biolife/ProductImage/Product5_13.jpg'),
								(5, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086970/Biolife/ProductImage/Product5_14.jpg'),
								(5, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682086973/Biolife/ProductImage/Product5_15.jpg'),
								(6, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087254/Biolife/ProductImage/Product6_16.jpg'),
								(6, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087256/Biolife/ProductImage/Product6_17.jpg'),
								(6, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087258/Biolife/ProductImage/Product6_18.jpg'),
								(6, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087262/Biolife/ProductImage/Product6_19.jpg'),
								(6, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087264/Biolife/ProductImage/Product6_20.jpg'),
								(7, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087521/Biolife/ProductImage/Product7_21.jpg'),
								(7, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087523/Biolife/ProductImage/Product7_22.jpg'),
								(7, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087526/Biolife/ProductImage/Product7_23.jpg'),
								(7, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087529/Biolife/ProductImage/Product7_24.jpg'),
								(8, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087716/Biolife/ProductImage/Product8_25.jpg'),
								(8, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087718/Biolife/ProductImage/Product8_26.jpg'),
								(8, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087720/Biolife/ProductImage/Product8_27.jpg'),
								(9, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087872/Biolife/ProductImage/Product9_28.jpg'),
								(9, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087874/Biolife/ProductImage/Product9_29.jpg'),
								(9, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682087876/Biolife/ProductImage/Product9_30.jpg'),
								(10, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088014/Biolife/ProductImage/Product10_31.jpg'),
								(10, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088015/Biolife/ProductImage/Product10_32.jpg'),
								(11, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088253/Biolife/ProductImage/Product11_33.jpg'),
								(12, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088338/Biolife/ProductImage/Product12_34.jpg'),
								(12, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088340/Biolife/ProductImage/Product12_35.jpg'),
								(13, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088560/Biolife/ProductImage/Product13_36.jpg'),
								(14, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088660/Biolife/ProductImage/Product14_37.jpg'),
								(15, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088732/Biolife/ProductImage/Product15_38.jpg'),
								(16, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682088947/Biolife/ProductImage/Product16_39.jpg'),
								(17, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089026/Biolife/ProductImage/Product17_40.jpg'),
								(17, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089028/Biolife/ProductImage/Product17_41.jpg'),
								(18, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089128/Biolife/ProductImage/Product18_42.jpg'),
								(18, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089130/Biolife/ProductImage/Product18_43.jpg'),
								(19, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089298/Biolife/ProductImage/Product19_44.jpg'),
								(19, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089302/Biolife/ProductImage/Product19_45.jpg'),
								(20, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089409/Biolife/ProductImage/Product20_46.jpg'),
								(21, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089542/Biolife/ProductImage/Product21_47.jpg'),
								(21, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089545/Biolife/ProductImage/Product21_48.jpg'),
								(22, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089680/Biolife/ProductImage/Product22_49.jpg'),
								(22, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089683/Biolife/ProductImage/Product22_50.jpg'),
								(22, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089686/Biolife/ProductImage/Product22_51.jpg'),
								(23, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089847/Biolife/ProductImage/Product23_52.jpg'),
								(24, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682089972/Biolife/ProductImage/Product24_53.jpg'),
								(25, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682090089/Biolife/ProductImage/Product25_54.jpg'),
								(25, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682090092/Biolife/ProductImage/Product25_55.jpg'),
								(25, 'https://res.cloudinary.com/dk6tfexdn/image/upload/v1682090096/Biolife/ProductImage/Product25_56.jpg')

insert into Promotion values ('Day of Southern Liberation for National Reunification', '2023-04-24', '2023-05-06')

insert into PromotionDetails values (1, 1, 0.3),
									(1, 4, 0.1),
									(1, 6, 0.5),
									(1, 9, 0.2),
									(1, 12, 0.2),
									(1, 15, 0.4),
									(1, 16, 0.1),
									(1, 19, 0.3),
									(1, 21, 0.4),
									(1, 24, 0.5)

insert into Orders values (1, '2023-04-28 10:41:17.557', '2023-04-28 12:32:44.213', 99.97, null, 4),
						  (2, '2023-04-29 8:21:12.43', '2023-04-29 9:50:25.433', 104.07, null, 4),
						  (3, '2023-04-29 16:39:32.447', null, 207.15, null, 5),
						  (2, '2023-05-01 9:22:19.15', '2023-05-01 9:57:51.145', 51.48, null, 4),
						  (3, '2023-05-01 9:51:43.85', '2023-05-01 10:40:18.425', 51.583, null, 4),
						  (1, '2023-05-01 12:39:41.75', '2023-05-01 13:42:17.765', 51.043, null, 3),
						  (1, '2023-05-02 9:04:35.115', '2023-05-02 9:49:31.15', 52.279, null, 3),
						  (1, '2023-05-02 14:53:22.95', '2023-05-02 15:39:12.195', 57.176, null, 3),
						  (3, '2023-05-03 15:12:29.135', null, 31.532, null, 5),
						  (3, '2023-05-04 12:41:02.807', null, 71.914, null, 2),
						  (1, '2023-05-04 12:59:26.495', null, 151.28, null, 2),
						  (2, '2023-05-04 13:35:19.15', null, 76.32, null, 1)

insert into OrderDetails values (1, 1, 1, 7.99, 0),
								(1, 2, 2, 10.49, 0),
								(1, 3, 2, 35.5, 0),
								(2, 6, 1, 13.49, 0),
								(2, 10, 1, 10.58, 0),
								(2, 22, 1, 80, 0),
								(3, 16, 1, 117.5, 0.3),
								(3, 17, 2, 62.45, 0),
								(4, 1, 2, 7.99, 0),
								(4, 3, 1, 35.5, 0),
								(5, 1, 1, 7.99, 0.3),
								(5, 2, 1, 10.49, 0),
								(5, 3, 1, 35.5, 0),
								(6, 1, 1, 7.99, 0.3),
								(6, 4, 1, 50.5, 0.1),
								(7, 1, 3, 7.99, 0.3),
								(7, 3, 1, 35.5, 0),
								(8, 1, 2, 7.99, 0.3),
								(8, 2, 1, 10.49, 0),
								(8, 3, 1, 35.5, 0),
								(9, 2, 1, 10.49, 0),
								(9, 8, 1, 9.29, 0),
								(9, 9, 1, 14.69, 0.2),
								(10, 11, 1, 5.69, 0),
								(10, 15, 1, 6.29, 0.4),
								(10, 17, 1, 62.45, 0),
								(11, 24, 1, 25.16, 0.5),
								(11, 22, 1, 80, 0),
								(11, 20, 1, 58.7, 0),
								(12, 10, 1, 10.58, 0),
								(12, 14, 1, 3.29, 0),
								(12, 17, 1, 62.45, 0)

insert into Feedback values ('NTDThanh', null, 1, '2023-04-22', 'The fruit is very fresh, canned clean and discreet, goods of clear origin are very reassuring.', 5, 1),
							('NTDThanh', null, 1, '2023-04-22', 'Biolife fast delivery and very good attitude', 5, 1),
							('NTDThanh', null, 2, '2023-04-22', 'Not fresh', 2, 1),
							('NTDThanh', null, 6, '2023-04-24', 'This tomato is delicious, quite large and succulent.', 5, 1)

