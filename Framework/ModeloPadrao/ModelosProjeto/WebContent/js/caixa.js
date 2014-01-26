function showInfo(status) {
	var elementById = document.getElementById('ulMeta');
	var list = elementById;

	if (status) {
		list.style.visibility = 'visible';
	} else {
		list.style.visibility = 'hidden';
	}
}

function validaNumeros(comp) {
	if (isNaN(comp.value)) {
		comp.value = '';
		return false;
	}

	return true;

}

function focusFrequencia(radio) {
	var grid = radio.id.split(":");
	var id = grid[0] + ":" + grid[1] + ":" + grid[2] + ":";
	
	if (radio.value == 1) {
		document.getElementById(id + "btnCancelar").focus();
	} else if (radio.value == 2) {
		setTimeout(focusMinutos(id), 1000);
	} else{
		alert("ELse:" + radio.value);
	}
}

function focusMinutos(id){
	document.getElementById(id + "input_minutos").focus();
	clearTimeout();
}

function chamaHover(elemento){
	alert("ID:" + elemento.id);
	elemento.style.backgroundColor = white;
	elemento.style.color = "#FF6600";
}

function chamaOut(elemento){
	elemento.style.backgroundColor = black;
	elemento.style.color = white;
}

function somenteNumero(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla==8 || tecla==0) return true;
	else return false;
    }
}

function validaCPF(ev, campo) {
	var tecla=(window.event)?event.keyCode:ev.which;
	if(tecla == 8) {
		return true;
	}
	if(somenteNumero(ev)) {
		var texto = document.getElementById(campo).value;
		if(texto.length < 11) {
			return true; 
		}
	}
	
	return false;
}
function formataCPF(element) {
    var valorNumerico = "";
    // ##retira valor nao numericos##
    for (i = 0; i < element.value.length; i++) {
        if (IsNumeric(element.value.charAt(i))) {
            valorNumerico += element.value.charAt(i);
        }
    }

    element.value = valorNumerico;
}

function formataNIS(element) {
    var valorNumerico = "";
    // ##retira valor nao numericos##
    for (i = 0; i < element.value.length; i++) {
        if (IsNumeric(element.value.charAt(i))) {
            valorNumerico += element.value.charAt(i);
        }
    }

    element.value = valorNumerico;
}

function formataNumero(element) {
	var valorNumerico = "";
	// ##retira valor nao numericos##
	for (i = 0; i < element.value.length; i++) {
		if (IsNumeric(element.value.charAt(i))) {
			valorNumerico += element.value.charAt(i);
		}
	}
	
	element.value = valorNumerico;
}

function formataData(element) {
    var valorNumerico = "";
    // ##retira valor nao numericos##
    //for (i = 0; i < element.value.length; i++) {
    for (i = 0; i < 8; i++) {
        if (IsNumeric(element.value.charAt(i))) {
            valorNumerico += element.value.charAt(i);
        }
    }

    element.value = valorNumerico;

}
function mascaraData(campoData){
    var data = campoData.value;
    if (data.length == 2){
        data = data + '/';
        document.forms[0].data.value = data;
        return true;              
    }
    if (data.length == 5){
        data = data + '/';
        document.forms[0].data.value = data;
        return true;
    }
}

function replaceAll(string, strAnt, strNew) {
	while (string.indexOf(strAnt) != -1) {
 		string = string.replace(strAnt, strNew);
	}
	return string;
}

function validaData(element) {
	//alert(element.value);
    var date = element.value;
    //debugger;
    if(replaceAll(replaceAll(date, "_", ""), "/", "") == "") {
    	//alert('Teste');
    	element.value = "";
    	return true;
    }
    var ardt = new Array;
    var ExpReg = new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
    ardt = date.split("/");
    //alert(ardt);
    erro = false;
    if (date.search(ExpReg) == -1) {
        erro = true;
    } else if (((ardt[1] == 4) || (ardt[1] == 6) || (ardt[1] == 9) || (ardt[1] == 11)) && (ardt[0] > 30)) erro = true;
    else if (ardt[1] == 2) {
    	
        if ((ardt[0] > 28) && ((ardt[2] % 4) != 0)) erro = true;
        if ((ardt[0] > 29) && ((ardt[2] % 4) == 0)) erro = true;
    }
    if (erro) {
        alert("\"" + element.value + "\" não é uma data válida!");
        element.focus();
        element.value = "";
        return false;
    }
    //alert('DEU CERTO');
    return true;
}

function inicialData(element)
{
    element.mask('99/99/9999'); 
}

function formataCNPJ(element) {
	var valorNumerico = "";
	// ##retira valor nao numericos##
	for (i = 0; i < element.value.length; i++) {
		if (IsNumeric(element.value.charAt(i))) {
			valorNumerico += element.value.charAt(i);
		}
	}
	
	element.value = valorNumerico;
}

function validaCNPJ(ev, campo) {
	var tecla=(window.event)?event.keyCode:ev.which;
	if(tecla == 8) {
		return true;
	}
	if(somenteNumero(ev)) {
		var texto = document.getElementById(campo).value;
		if(texto.length < 14) {
			return true; 
		}
	}
	
	return false;
}

function validaCNPJCad(ev) {
	return validaCNPJ(ev, 'inputNuIdentificadorCliCNPJCad');
}

function validaCNPJPesq(ev) {
	return validaCNPJ(ev, 'inputNuIdentificadorCliCNPJPesq');
}

function validaCNPJFiltro(ev) {
	return validaCNPJ(ev, 'inputNuIdentificadorCliCNPJFiltro');
}

function validaCPFCad(ev) {
	return validaCPF(ev, 'inputNuIdentificadorCliCPFCad');
}

function validaCPFPesq(ev) {
	return validaCPF(ev, 'inputNuIdentificadorCliCPFPesq');
}

function validaCPFFiltro(ev) {
	return validaCPF(ev, 'inputNuIdentificadorCliCPFFiltro');
}

function limpaMascara(e) {
	var conteudo = e.value;
	while(conteudo.indexOf('.') != -1) {
		conteudo = conteudo.replace(".", "");
	}
	
	while(conteudo.indexOf('-') != -1) {
		conteudo = conteudo.replace("-", "");
	}
	
	while(conteudo.indexOf('/') != -1) {
		conteudo = conteudo.replace("/", "");
	}
	
	document.getElementById(e.id).value = conteudo;
}

function aplicaMascara(e) {
	var conteudo = e.value;
	if(conteudo.length == 0){
		return;
	}
	if(conteudo.length == 11) {
		var aux = "";
		var offset = 3;
		var count = 0;
		for(count = 0; count < conteudo.length - 2; count++) {
			if(count == offset) {
				aux += ".";
				offset += 3;
			}
			aux += conteudo.charAt(count);
		}
		aux += "-" + conteudo.substring(count, conteudo.length);
		document.getElementById(e.id).value = aux;
	}
}

function aplicaMascaraCNPJ(e) {
	var conteudo = e.value;
	if(conteudo.length == 0){
		return;
	}
	if(conteudo.length == 14) {
		var aux = "";
		var offset = 2;
		var count = 0;
		for(count = 0; count < conteudo.length - 2; count++) {
			if(count == offset) {
				if(offset == 8) {
					aux += "/";
					offset += 4;
				} else {
					aux += ".";
					offset += 3;
				}
			}
			aux += conteudo.charAt(count);
		}
		aux += "-" + conteudo.substring(count, conteudo.length);
		document.getElementById(e.id).value = aux;
	}
}

function doInconforme(element, idApontamento, arrayAp, idItem, arrayIt, idBloco) {
	var elementApontamento = document.getElementById("formEfetuaVerificacao:iconApontamento" + idApontamento);
	var elementItem = document.getElementById("formEfetuaVerificacao:iconItem" + idItem);
	var elementBloco = document.getElementById("formEfetuaVerificacao:iconBloco" + idBloco);
	
	if(element.checked){
			elementApontamento.src = elementApontamento.src.replace('apontamento.gif', 'apontamentoI.gif');
			elementApontamento.src = elementApontamento.src.replace('apontamentoC.gif', 'apontamentoI.gif');
			elementItem.src = elementItem.src.replace('item.gif', 'itemI.gif');
			elementItem.src = elementItem.src.replace('itemC.gif', 'itemI.gif');
			elementBloco.src = elementBloco.src.replace('bloco.gif', 'blocoI.gif');
			elementBloco.src = elementBloco.src.replace('blocoC.gif', 'blocoI.gif');
			for(var i = 0; i < arrayAp.length; i++){
				if(arrayAp[i] == 0) continue;
				if(!document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).checked){
					document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]).src = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]).src.replace('apontamento.gif', 'apontamentoC.gif');
				}
			}
	}else{
		elementApontamento.src = elementApontamento.src.replace('apontamentoI.gif', 'apontamento.gif');
		var changeToInconformeIt = true;
		for(var i = 0; i < arrayAp.length; i++){
			if(arrayAp[i] == 0) continue;
			if(document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).checked){
				changeToInconformeIt = false;
				break;
			}
		}
		if(changeToInconformeIt){
			elementItem.src = elementItem.src.replace('itemI.gif', 'item.gif');
			for(var i = 0; i < arrayAp.length; i++){
				if(arrayAp[i] == 0) continue;
				if(!document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).checked){
					document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]).src = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]).src.replace('apontamentoC.gif', 'apontamento.gif');
				}
			}
		}else{
			for(var i = 0; i < arrayAp.length; i++){
				if(arrayAp[i] == 0) continue;
				if(!document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).checked){
					document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]).src = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]).src.replace('apontamento.gif', 'apontamentoC.gif');
				}
			}
		}
		var changeToInconformeBl = true;
		for(var i = 0; i < arrayIt.length; i++){
			if(arrayIt[i] == 0) continue;
			if(document.getElementById("formEfetuaVerificacao:iconItem" + arrayIt[i]).src.indexOf("itemI") != -1){
				changeToInconformeBl = false;
				break;
			}
		}
		if(changeToInconformeBl){
			elementBloco.src = elementBloco.src.replace('blocoI.gif', 'bloco.gif');
		}
	}
}
function doItemConforme(element, arrayAp, idIt, arrayIt, idBl) {
	
	var elementItem = document.getElementById("formEfetuaVerificacao:iconItem" + idIt);
	var elementBloco = document.getElementById("formEfetuaVerificacao:iconBloco" + idBl);
	if(element.checked){
		elementItem.src = elementItem.src.replace('item.gif', 'itemC.gif');
		elementItem.src = elementItem.src.replace('itemI.gif', 'itemC.gif');
		for(var i = 0; i < arrayAp.length; i++){
			if(arrayAp[i] == 0) continue;
			var elementApontamento = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]);
			elementApontamento.src = elementApontamento.src.replace('apontamento.gif', 'apontamentoC.gif');
			elementApontamento.src = elementApontamento.src.replace('apontamentoI.gif', 'apontamentoC.gif');
			document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).checked = false;
			document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).disabled = true;		
		}
		var changeToConforme = true;
		var changeToNaoVerificado = true;
		for(var i = 0; i < arrayIt.length; i++){
			if(arrayIt[i] == 0 || 
					(document.getElementById("formEfetuaVerificacao:ckItemDisabled" + arrayIt[i]) != null && 
					document.getElementById("formEfetuaVerificacao:ckItemDisabled" + arrayIt[i]).checked)) continue;
			var elementItem = document.getElementById("formEfetuaVerificacao:iconItem" + arrayIt[i]);
			if(elementItem.src.indexOf("itemI.gif") != -1){
				changeToConforme = false;
				changeToNaoVerificado = false;
				break;
			}
			else if(elementItem.src.indexOf("item.gif") != -1){
				changeToConforme = false;
			}
		}
		if(changeToConforme){
			elementBloco.src = elementBloco.src.replace('bloco.gif', 'blocoC.gif');
			elementBloco.src = elementBloco.src.replace('blocoI.gif', 'blocoC.gif');
		}else if(changeToNaoVerificado){
			elementBloco.src = elementBloco.src.replace('blocoI.gif', 'bloco.gif');
			elementBloco.src = elementBloco.src.replace('blocoC.gif', 'bloco.gif');
		}else{
			elementBloco.src = elementBloco.src.replace('bloco.gif', 'blocoI.gif');
			elementBloco.src = elementBloco.src.replace('blocoC.gif', 'blocoI.gif');
		}
	} else{
		elementItem.src = elementItem.src.replace('itemC.gif', 'item.gif');
		elementBloco.src = elementBloco.src.replace('blocoC.gif', 'bloco.gif');
		for(var i = 0; i < arrayAp.length; i++){
			if(arrayAp[i] == 0) continue;
			var elementApontamento = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]);
			elementApontamento.src = elementApontamento.src.replace('apontamentoC.gif', 'apontamento.gif');
			document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).disabled = false;
		}
	}
}


function doItemDesabilita(element, arrayAp, idIt, arrayIt, idBl) {

	var elementItem = document.getElementById("formEfetuaVerificacao:iconItem" + idIt);
	var elementBloco = document.getElementById("formEfetuaVerificacao:iconBloco" + idBl);
	if(element.checked){
		elementItem.src = elementItem.src.replace('itemC.gif', 'item.gif');
		elementItem.src = elementItem.src.replace('itemI.gif', 'item.gif');
		
		document.getElementById("formEfetuaVerificacao:ckItemConform" + idIt).checked = false;
		document.getElementById("formEfetuaVerificacao:ckItemConform" + idIt).disabled = true;
		
		for(var i = 0; i < arrayAp.length; i++){
			if(arrayAp[i] == 0) continue;
			var elementApontamento = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]);
			elementApontamento.src = elementApontamento.src.replace('apontamentoC.gif', 'apontamento.gif');
			elementApontamento.src = elementApontamento.src.replace('apontamentoI.gif', 'apontamento.gif');
			document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).checked = false;
			document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).disabled = true;		
		}
		var changeToConforme = true;
		var changeToNaoVerificado = true;
		for(var i = 0; i < arrayIt.length; i++){
			if(arrayIt[i] == 0 || 
					(document.getElementById("formEfetuaVerificacao:ckItemDisabled" + arrayIt[i]) != null && 
					document.getElementById("formEfetuaVerificacao:ckItemDisabled" + arrayIt[i]).checked)) continue;
			var elementItem = document.getElementById("formEfetuaVerificacao:iconItem" + arrayIt[i]);
			if(elementItem.src.indexOf("itemI.gif") != -1){
				changeToConforme = false;
				changeToNaoVerificado = false;
				break;
			}
			else if(elementItem.src.indexOf("item.gif") != -1){
				changeToConforme = false;
			}
		}
		if(changeToConforme){
			elementBloco.src = elementBloco.src.replace('bloco.gif', 'blocoC.gif');
			elementBloco.src = elementBloco.src.replace('blocoI.gif', 'blocoC.gif');
		}else if(changeToNaoVerificado){
			elementBloco.src = elementBloco.src.replace('blocoI.gif', 'bloco.gif');
			elementBloco.src = elementBloco.src.replace('blocoC.gif', 'bloco.gif');
		}else{
			elementBloco.src = elementBloco.src.replace('bloco.gif', 'blocoI.gif');
			elementBloco.src = elementBloco.src.replace('blocoC.gif', 'blocoI.gif');
		}
	} else{
		elementItem.src = elementItem.src.replace('itemC.gif', 'item.gif');
		elementBloco.src = elementBloco.src.replace('blocoC.gif', 'bloco.gif');
		
		document.getElementById("formEfetuaVerificacao:ckItemConform" + idIt).checked = false;
		document.getElementById("formEfetuaVerificacao:ckItemConform" + idIt).disabled = false;
		
		for(var i = 0; i < arrayAp.length; i++){
			if(arrayAp[i] == 0) continue;
			var elementApontamento = document.getElementById("formEfetuaVerificacao:iconApontamento" + arrayAp[i]);
			elementApontamento.src = elementApontamento.src.replace('apontamentoC.gif', 'apontamento.gif');
			document.getElementById("formEfetuaVerificacao:ckApontamento" + arrayAp[i]).disabled = false;
		}
	}
}
var abreApontamento = true;

function openAllChildren(event, arrayItem) {
	if(!abreApontamento){
		abreApontamento = true;
		return;
	}
	
	for(var i = 0; i < arrayItem.length; i++){
		if(arrayItem[i] != 0){
			if(document.getElementById("formEfetuaVerificacao:toggleItem" + arrayItem[i] + "_body").style.display == "none"){
				SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:toggleItem' + arrayItem[i]);			
			}
		}
	}
}
function openAllBlocos(event, stringBloco) {
	arrayBloco = stringBloco.split(",");
	var ancor = 0;
	for(var i = 0; i < arrayBloco.length; i++){
		if(arrayBloco[i] != 0){
			if(i == 0){
				ancor = arrayBloco[i];
			} else
			if(document.getElementById("formEfetuaVerificacao:toggleBloco" + arrayBloco[i] + "_body").style.display == "none"){
				abreApontamento = false;
				SimpleTogglePanelManager.toggleOnClient(event,'formEfetuaVerificacao:toggleBloco' + arrayBloco[i]);	
			}
		}
	}
	if(ancor != 0){
		this.location = "#item" + ancor;		
	}
}

var arrayIconBloco = new Array();
function closeBlocoNaoPrejudicado(event, blocoPrejudicado, blocoParaFechar) {
	var idIcon = "formEfetuaVerificacao:iconBloco" ;
	var idToggle = "formEfetuaVerificacao:toggleBloco";
	var idBtn = "formEfetuaVerificacao:btnObsBloco";
	var chkBoxDisabled = "formEfetuaVerificacao:ckBloco";
	var iconBloco = document.getElementById(idIcon + blocoPrejudicado);
	for(var i = 0; i < blocoParaFechar.length; i++){
		if(blocoParaFechar[i] != 0 && blocoParaFechar[i] != blocoPrejudicado){
			var elementBlocoIcon = document.getElementById(idIcon + blocoParaFechar[i]);
			var elementBlocoToogleH = document.getElementById(idToggle+blocoParaFechar[i]+'_header');
			var elementBlocoToogleB = document.getElementById(idToggle+blocoParaFechar[i]+'_body');
			var elementBlocoBtn = document.getElementById(idBtn+blocoParaFechar[i]);
			var elementBlocoChk = document.getElementById(chkBoxDisabled+blocoParaFechar[i]);
			//guardar em memoria os estados de cada bloco (conforme, inconforme, ñ verif.) para poder recuperar os icones dos blocos 
			if(elementBlocoIcon.src.indexOf('blocoP.gif') == -1){
				arrayIconBloco [i] = elementBlocoIcon.src; 	
				arrayIconBloco [i] = elementBlocoIcon.src.substr(elementBlocoIcon.src.indexOf("/images"));
			}
			if(navigator.appName == "Microsoft Internet Explorer") {
				//Verificação Prejudicada
				if(iconBloco.src.indexOf('blocoI.gif') >= 0) {
					//altera o icone para Prejudicado
					elementBlocoIcon.src = elementBlocoIcon.src.replace('bloco.gif', 'blocoP.gif');
					elementBlocoIcon.src = elementBlocoIcon.src.replace('blocoC.gif', 'blocoP.gif');
					elementBlocoIcon.src = elementBlocoIcon.src.replace('blocoI.gif', 'blocoP.gif');
					//retira evento onclick do bloco (para não poder abrir o bloco)
					 elementBlocoToogleH.onclick = null;
					 //fecha o bloco;
					 elementBlocoToogleB.style.display = 'none';
					 if(elementBlocoBtn.onclick.indexOf('return false   ;') == -1){
						 //retira o evento onclick do botão observação (para não poder abrir o modal observação)
						 elementBlocoBtn.onclick = "return false   ;" + elementBlocoBtn.onclick;
					 }
					 //verifica se existe o checkbox desabilita
					 if(elementBlocoChk != null){
						 //disabilida o checkbox "Disabilita"
						 elementBlocoChk.disabled = true;
					 }
					 //Verificação Não Prejudicada
				}else{
					elementBlocoIcon.src = elementBlocoIcon.src.substr(0, elementBlocoIcon.src.indexOf("/images")) + arrayIconBloco[i];
					elementBlocoToogleB.style.display = 'none';

					// Valida se existe botão de "Desabilitar" e se ele está não está checado. Caso não exista ou ele não esteja marcado, ou seja, o bloco está habilitado portanto pode ser aberto. 
					if(elementBlocoChk == null || !elementBlocoChk.checked) {
						document.getElementById(idToggle+blocoParaFechar[i]+'_header').onclick = function(){ SimpleTogglePanelManager.toggleOnClient(event, this.parentNode.id); };
					}
					
					//verifica se existe o checkbox desabilita
			        if(elementBlocoChk != null){
			        	//disabilida o checkbox "Disabilita"
			        	elementBlocoChk.disabled = false;
			        }
				}
			}else{
				if(iconBloco.src.indexOf('blocoI.gif') >= 0){
					elementBlocoIcon.src = elementBlocoIcon.src.replace('bloco.gif', 'blocoP.gif');
					elementBlocoIcon.src = elementBlocoIcon.src.replace('blocoC.gif', 'blocoP.gif');
					elementBlocoIcon.src = elementBlocoIcon.src.replace('blocoI.gif', 'blocoP.gif');
					elementBlocoToogleH.setAttribute("onclick", "");
					elementBlocoToogleB.style.display = 'none';
					if(elementBlocoBtn.getAttribute("onclick").indexOf('return false   ;') == -1){
						 elementBlocoBtn.setAttribute("onclick", "return false   ;" + elementBlocoBtn.getAttribute("onclick"));
					}
					//verifica se existe o checkbox desabilita
					if(elementBlocoChk != null){
					 //disabilida o checkbox "Disabilita"
					 elementBlocoChk.disabled = true;
					}
				}else{
					elementBlocoIcon.src = elementBlocoIcon.src.substr(0, elementBlocoIcon.src.indexOf("/images")) + arrayIconBloco[i];
					elementBlocoToogleB.style.display = 'none';
					elementBlocoBtn.setAttribute("onclick", elementBlocoBtn.getAttribute("onclick").replace("return false   ;", ""));
					
					if(elementBlocoChk == null || !elementBlocoChk.checked) {
						elementBlocoToogleH.setAttribute("onclick", "SimpleTogglePanelManager.toggleOnClient(event, '"+idToggle+blocoParaFechar[i]+"');");
					}
					//verifica se existe o checkbox desabilita
					 if(elementBlocoChk != null){
						 //disabilida o checkbox "Disabilita"
						 elementBlocoChk.disabled = false;
					 }
				}
			}
		}
	}
}