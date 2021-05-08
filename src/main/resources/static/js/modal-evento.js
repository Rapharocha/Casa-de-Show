$('#exclusaoModal').on('show.bs.modal', function(event) {
	
	var button = $(event.relatedTarget);
	
	var idEvento = button.data('codigo')
	var nomeEvento = button.data('nome')
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if(!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + idEvento);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o evento <strong>' + nomeEvento + '</strong>?');
});

$(function(){
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({decimal: ',', thousands: '.', allowZero: true});
});