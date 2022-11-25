let product = document.getElementById("producto_id")
let tb = document.getElementById("tb")
let btnSubmit = document.getElementById("btn_submit")
let subtotal = document.getElementById("subtotal")
let total = document.getElementById("total")
let clientId = document.getElementById("client_id")
let inputClient = document.getElementById("inputClient")
let btnAdd = document.getElementById("btn_agregar")

let subtotalSale = 0
subtotal.innerHTML = 0
let totalSale = 0
total.innerHTML = 0

const handleButtonSubmit = (enabled) =>
  enabled
    ? btnSubmit.removeAttribute("disabled")
    : btnSubmit.setAttribute("disabled", "true")

const removeElement = (id) => {
  subtotalSale =
    subtotalSale - Number(document.getElementById("imp" + id).textContent, 10)

  totalSale = subtotalSale * 0.18 + subtotalSale
  subtotal.innerHTML = subtotalSale
  total.innerHTML = totalSale
  document.getElementById(id).remove()
  handleButtonSubmit(totalSale)
}

clientId.addEventListener("change", (e) => (inputClient.value = e.target.value))

product.addEventListener("change", (e) => {
  let stock = product.value.split("!")[4]
  document.getElementById("showStock").value = stock
})

btnAdd.addEventListener("click", (e) => {
  if (clientId.value === "null") return alert("Seleccione un cliente")
  if (!product.value) return alert("Seleccione producto")

  let content = product.value.split("!")
  let id = content[0]
  let description = content[1]
  let image = content[2]
  let price = content[3]
  let quantityStock = content[4]
  let quantityProductToSell = document.getElementById(
    "quantity_product_sell"
  ).value

  if (document.getElementById(id)) return alert("Acción invalida")

  if (quantityProductToSell > parseInt(quantityStock)) return alert("Sin stock")

  subtotalSale += price * quantityProductToSell
  totalSale = subtotalSale * 0.18 + subtotalSale
  subtotal.innerHTML = subtotalSale
  total.innerHTML = totalSale
  handleButtonSubmit(total)

  tb.innerHTML += `
                		<tr class="border-bottom text-center" id="${id}">
                             <td>${id}<input type="hidden" name="products" value="${id}"/></td>
                             <td>${description}</td>
                             <td>
                               <img src="${image}" class="img-fluid btn-circle" />
                             </td>
                             <td>${price}</td>
                             <td>${quantityProductToSell}<input type="hidden" name="quantities" value="${quantityProductToSell}"/></td>
                             <td id="imp${id}">${
    quantityProductToSell * price
  }</td>
                             <td class="text-center">
                               <a
                                 onClick='removeElement("${id}")'
                                 class="btn btn-danger btn-circle btn-sm"
                                 ><i class="fas fa-trash"></i
                               ></a>
                             </td>
                           </tr>`
})
