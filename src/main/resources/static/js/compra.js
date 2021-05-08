$('#confirmacaoCompra').on('show.bs.modal', function(event) {
	
	var button = $(event.relatedTarget);
	
	var idEvento = button.data('id');
	var nomeEvento = button.data('nome')
	var username = button.data('username')
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url');
	
	if(!action.endsWith('/')) {
		action += '/';
	}
	
	action += idEvento;
	
	if(!action.endsWith('/')) {
		action += '/';
	}
	
	form.attr('action', action + username);
	
	
	modal.find('.modal-body span').html('Tem certeza que deseja comprar o evento <strong>' + nomeEvento + '</strong>?');
});