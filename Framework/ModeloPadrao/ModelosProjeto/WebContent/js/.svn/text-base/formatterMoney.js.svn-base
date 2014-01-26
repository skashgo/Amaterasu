//Este javascript cria uma formatacao para valores monetarios.
function maxSize(element, length){
	if(element.value.length > length){
		element.value = element.value.substring(0, length);
	}
}

function inicial(element) {
	if (element.value.length < 4) {
		element.value = "R$ 0,00";
	}
	setCursorPositionToEnd(element.id);
}

function limpa(element) {
	if (element.value == "R$ 0,00") {
		element.value = "";
	}
}

function setCursorPositionToEnd(elementId) {
	var elementRef = document.getElementById(elementId);
	var cursorPosition = document.getElementById(elementId).value.length;

	if (elementRef != null) {
		if (elementRef.createTextRange) {
			var textRange = elementRef.createTextRange();
			textRange.move('character', cursorPosition);
			textRange.select();
		} else {
			if (elementRef.selectionStart) {
				elementRef.focus();
				elementRef.setSelectionRange(cursorPosition, cursorPosition);
			} else {
				elementRef.focus();
			}
		}
	}
}

// valida se o value é um valor numerico##
function IsNumeric(value) {
	var er = /^[0-9]+$/;
	return (er.test(value)) ? true : false;
}

function formata(element) {
	var valorNumerico = "";
	// ##retira valor nao numericos##
	for (i = 0; i < element.value.length; i++) {
		if (IsNumeric(element.value.charAt(i))) {
			valorNumerico += element.value.charAt(i);
		}
	}
	var size = valorNumerico.length;
	var zeroEsq = "";
	var notZero = false;
	var valorSemZero = "";

	// ##retira os zeros a esquerda##
	if (size > 3) {
		for (i = 0; i < size; i++) {
			if (notZero || valorNumerico.charAt(i) != '0') {
				notZero = true;
				valorSemZero += valorNumerico.charAt(i);
			}
		}
	} else {
		valorSemZero = valorNumerico;
	}

	size = valorSemZero.length;
	// ##se menor que 3 digitos add zeros a esquerda##
	if (size < 3) {
		for (i = size; i < 3; i++) {
			zeroEsq += '0';
		}
	}
	valorNumerico = zeroEsq + valorSemZero;
	size = valorNumerico.length;
	var valorFormatado = "";
	// ##insere a virgula separando as casas decimais##
	for (i = 0; i < size; i++) {
		if (i == (size - 2)) {
			valorFormatado += ',';
		}
		valorFormatado += valorNumerico.charAt(i);
	}
	var result = "";
	size = valorFormatado.length;
	var inteiro = size - 3;
	// ##insere a ponto separando as casas de milhares##
	for (i = 0; i < size; i++) {
		if (inteiro > 3) {
			if (i != 0 && i == (inteiro % 3)) {
				result += '.';
			} else {
				if (i > 0 && (i - inteiro % 3) % 3 == 0 && i < inteiro) {
					result += '.';

				}
			}
		}
		result += valorFormatado.charAt(i);
	}
	element.value = "R$ " + result;
}
// #########################---EXEMPLO---###############################################
//
// <input type="text" id="valor" onblur="limpa(this)" onfocus="inicial(this)"
// onkeyup="formata(this)">
//
// #####################################################################################
