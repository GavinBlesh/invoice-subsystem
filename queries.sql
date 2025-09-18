-- Computer Science II
-- Assignment 4.0
-- Queries
--
-- Name: Gavin Blesh

-- 1. A query to retrieve the main attributes of each person (their UUID, and last/first name)
select personUUID, firstName, lastName from Person;

-- 2. A query to retrieve the major fields for every person including their email address(es)
select * from Person p left join Email e on p.personId = e.personId;

-- 3. A query to get the email addresses of a specific person
select p.personId, p.personUUID, p.firstName, p.lastName, e.email from Person p left join Email e on p.personId = e.personId where p.personId = 1;

-- 4. A query to change the email address of a specific email record
update Email set email = 'bgerrie0@ChangedMyEmailInSQL.com' where emailID = 5;

-- 5. A query (or series of queries) to remove a specific person record
-- --------------------------------------------------------------------------------------------------
delete ii from InvoiceItem ii
join Invoice inv on ii.invoiceId = inv.invoiceId
join Person p on inv.salesPersonId = p.personId
where p.personId = 1;

delete ii from InvoiceItem ii
join Invoice inv on ii.invoiceId = inv.invoiceId
join Company c on inv.customerId = c.companyId
join Person p on c.contactId = p.personId
where p.personId = 1;

delete ii from InvoiceItem ii
join Item i on ii.itemid = i.itemId
join Company c on i.servicerId = c.companyId
join Person p on c.contactId = p.personId
where p.personId = 1;

delete i from Item i
join Company c on i.servicerId = c.companyId
join Person p on p.personId = c.contactId
where p.personId = 1;
    
delete from Invoice where
salesPersonId = (select personId from Person where personid = 1);
    
delete inv from Invoice inv
join Company c on c.companyId = inv.customerId
join Person p on c.contactId = p.personId
where p.personId = 1;
    
delete from Company where 
contactId = (select personId from Person where personId = 1);
    
delete from Email where personId = 1;
delete from Person where personId = 1;
-- --------------------------------------------------------------------------------------

-- 6. A query to get all the items on a specific invoice record
select ii.invoiceId, ii.invoiceItemId, i.name, 
i.itemUUID, i.itemId, i.itemType, i.modelNumber, i.retailCost, 
ii.purchaseType, ii.quantity, ii.amount, ii.hoursRented, ii.startDate, ii.endDate
from InvoiceItem ii join Item i on ii.itemId = i.itemId where ii.invoiceId = 3;

-- 7. A query to get all the items purchased by a specific customer
select * from 
Invoice inv join Company c on inv.customerId = c.companyId
join InvoiceItem ii on inv.invoiceId = ii.invoiceId
join Item i on ii.itemId = i.itemId
where customerId = 1;

-- 8. A query to find the total number of invoices for each customer even if they do not have any*
select c.companyId, c.companyUUID, c.name, count(inv.invoiceId)
from Company c
left join Invoice inv on c.companyId = inv.customerId
group by c.companyId;

-- 9. A query to find the total number of sales made by each salesperson; do not include anyone who has zero sales
select p.personUUID, p.firstName, p.lastName, count(inv.salesPersonId)
from Invoice inv
left join Person p on inv.salesPersonId = p.personId
group by inv.salesPersonId
having count(inv.salesPersonId) > 0;

-- 10. A query to find the subtotal charge of all materials purchased in each invoice. Does not include taxes.
select inv.invoiceUUID, c.name, sum(ii.quantity * it.pricePerUnit) as sumIn$
from InvoiceItem ii
join Invoice inv on ii.invoiceId = inv.invoiceId
join Item it on it.itemId = ii.itemId
join Company c on c.companyId = inv.customerId
where ii.quantity is not null and itemType = 'M'
group by inv.invoiceId;

-- 11. A query to find and report any invoice that includes multiple records of the same material
select ii.invoiceId, i.name, count(*), sum(ii.quantity)
from InvoiceItem ii
join Item i on ii.itemId = i.itemId
where quantity is not null
group by ii.invoiceId, i.itemId
having count(*) > 1;

-- 12. A query to detect a potential instance of fraud where an employee makes a sale to their own company
select *
from Invoice inv 
join Company c on inv.customerId = c.companyId
join Person p on p.personId = inv.salesPersonId
where inv.salesPersonId = c.contactId;



