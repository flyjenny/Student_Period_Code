select model, speed as gigahertz, hd as gigabytes from pc where price < 1000

select distinct maker from product where model in (select model from printer)

select model, hd from pc where speed = 3.2 and price < 2000

select maker, speed from product, laptop where product.model = laptop.model and hd > 30

select distinct maker from product, laptop where product.model = laptop.model and maker not in
(select maker from product, pc where product.model = pc.model)

select pc1.model, pc2.model from pc pc1, pc pc2 where pc1.model < pc2.model and pc1.ram = pc2.ram and pc1.speed = pc2.speed

select * from printer where price >= all(select price from printer)

(select model from pc where price >= all(select price from pc) and price >= all(select price from laptop) and  price >= all(select price from printer)) union
(select model from laptop where price >= all(select price from pc) and price >= all(select price from laptop) and  price >= all(select price from printer)) union
(select model from printer where price >= all(select price from pc) and price >= all(select price from laptop) and  price >= all(select price from printer))

select maker from product where model = (select model from printer where price <= all(select price from printer))

select avg(speed) from laptop where price > 1000

select avg(price) from (
	(select price from product, pc where product.model = pc.model and maker = 'D') union all
	(select price from product, laptop where product.model = laptop.model and maker = 'D')
	) l
	
select maker, avg(screen) from product, laptop where product.model = laptop.model group by maker
