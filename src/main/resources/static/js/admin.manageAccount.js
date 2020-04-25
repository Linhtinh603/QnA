$(document)
		.ready(
				function() {
					$('#tableAdmin')
							.DataTable(
									{
										language : {
											processing : "Traitement en cours...",
											search : "Tìm kiếm&nbsp;:",
											lengthMenu : "Hiển thị _MENU_ phần tử",
											info : "Hiển thị _START_ tới _END_ của _TOTAL_ phần tử",
//											infoEmpty : "Affichage de l'&eacute;lement 0 &agrave; 0 sur 0 &eacute;l&eacute;ments",
//											infoFiltered : "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
//											infoPostFix : "",
//											loadingRecords : "Chargement en cours...",
//											zeroRecords : "Aucun &eacute;l&eacute;ment &agrave; afficher",
//											emptyTable : "Aucune donnée disponible dans le tableau",
											paginate : {
												first : "Đầu tiên",
												previous : "Trước đó",
												next : "Tiến tới",
												last : "Cuối cùng"
											},
											aria : {
//												sortAscending : ": activer pour trier la colonne par ordre croissant",
//												sortDescending : ": activer pour trier la colonne par ordre décroissant"
											}
										}
									});
				});