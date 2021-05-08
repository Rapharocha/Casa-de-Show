$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	
	var button = $(event.relatedTarget);
	
	var idCasa = button.data('codigo')
	var nomeCasa = button.data('nome')
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if(!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + idCasa);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir a casa <strong>' + nomeCasa + '</strong>?');
});

$(function(){
	$('[rel="tooltip"]').tooltip();
});


