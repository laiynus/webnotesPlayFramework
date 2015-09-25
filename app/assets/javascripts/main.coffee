$(document).ready ->
    $("#addNote").on "click", ->
        note = $('#noteText').val()
        $("#errorBox").remove()
        $("#messageBox").remove()
        if(!note.trim())
          $( "<div id='errorBox' class='alert alert-danger fade in'>" +
              "<button type='button' class='close' data-dismiss='alert'>&times;</button>Note can't be empty!</div>")
            .insertBefore( "#addNote");
          $("#noteText").val('');
        else
          r = jsRoutes.controllers.Notes.create(note)
          $.ajax
            url:  r.url
            type: r.type
            context: this
            error: (jqXHR, textStatus, errorThrown) ->
              $( "<div id='errorBox' class='alert alert-danger fade in'>" +
                  "<button type='button' class='close' data-dismiss='alert'>&times;</button>" + textStatus + "</div>")
              .insertBefore( "#addNote")
              $("#noteText").val('')
            success: (data, textStatus, jqXHR) ->
              $( "<div id='messageBox' class='alert alert-success fade in'>" +
                  "<button type='button' class='close' data-dismiss='alert'>&times;</button>Note successfully added</div>")
              .insertBefore( "#addNote")
              $("#noteText").val('')
              rowCount = $('#noteTable tr').length
              if(rowCount<=10 || $("#switcher").val()=="all")
                date = new Date(data.dateTimeCreate)
                $("<tr><td style='display:none' id='" + data.id + "'>" + data.id + "</td><td>" + data.note + "</td><td>" + date + "<td><input type='button' value='Select note' onclick='selectNote(this)' class='btn btn-warning'/>" +  "</td></tr>").insertBefore('#noteTable > tbody > tr:first');

    $(document).ready getNotes =  ->
        switcher = $('#switcher').val()
        r = jsRoutes.controllers.Notes.getNotes(switcher)
        $.ajax
          url: r.url
          type: r.type
          context: this
          error: (jqXHR, textStatus, errorThrown) ->
            $( "<div id='errorBox' class='alert alert-danger fade in'>" +
                "<button type='button' class='close' data-dismiss='alert'>&times;</button>" + textStatus + "</div>")
            .insertBefore( "#addNote");
          success: (data, textStatus, jqXHR) ->
            if(data.length==0)
              $( "<div id='messageBox' class='alert alert-success fade in'>" +
                  "<button type='button' class='close' data-dismiss='alert'>&times;</button>You have no notes!</div>")
              .insertBefore( "#addNote");
            else
              $("#noteTableBody").empty();
              $.each data, (index, elem) ->
                date = new Date(elem.dateTimeCreate)
                $("#noteTable tbody").append("<tr><td style='display:none'>" + elem.id + "</td><td>" + elem.note + "</td><td>" + date + "<td><input type='button' value='Select note' onclick='selectNote(this)' class='btn btn-warning'/>" +  "</td></tr>")

    $(document).ready ->
      $("#switcherNote").on "click", ->
        $("#errorBox").remove()
        $("#messageBox").remove()
        if($("#switcher").val()=="last")
          $("#switcher").val("all");
          getNotes()
          $("#switcherNote").html('Show last notes')
        else
          $("#switcher").val("last")
          getNotes()
          $("#switcherNote").html('Show all notes')

    $(document).ready ->
    $("#deleteNote").on "click", ->
      id = $("#selectedNote").attr('name')
      $("#errorBox").remove()
      $("#messageBox").remove()
      r = jsRoutes.controllers.Notes.delete(id)
      $.ajax
        url:  r.url
        type: r.type
        context: this
        error: (jqXHR, textStatus, errorThrown) ->
          $( "<div id='errorBox' class='alert alert-danger fade in'>" +
              "<button type='button' class='close' data-dismiss='alert'>&times;</button>" + textStatus + "</div>")
          .insertBefore( "#addNote")
          $("#noteText").val('')
        success: (data, textStatus, jqXHR) ->
          $( "<div id='messageBox' class='alert alert-success fade in'>" +
              "<button type='button' class='close' data-dismiss='alert'>&times;</button>Note successfully deleted</div>")
          .insertBefore( "#addNote")
          $("#noteText").val('')
          $("#editNote").prop('disabled', true)
          $("#deleteNote").prop('disabled', true)
          $("#selectedNote").removeAttr("name")
          getNotes()
          $("tr:not(:has(#" + id +"))").remove()

    $(document).ready ->
    $("#editNote").on "click", ->
      id = $("#selectedNote").attr('name')
      note = $('#noteText').val()
      $("#errorBox").remove()
      $("#messageBox").remove()
      r = jsRoutes.controllers.Notes.update(id,note)
      $.ajax
        url:  r.url
        type: r.type
        context: this
        error: (jqXHR, textStatus, errorThrown) ->
          $( "<div id='errorBox' class='alert alert-danger fade in'>" +
              "<button type='button' class='close' data-dismiss='alert'>&times;</button>" + textStatus + "</div>")
          .insertBefore( "#addNote")
          $("#noteText").val('')
        success: (data, textStatus, jqXHR) ->
          $( "<div id='messageBox' class='alert alert-success fade in'>" +
              "<button type='button' class='close' data-dismiss='alert'>&times;</button>Note successfully updated</div>")
          .insertBefore( "#addNote")
          $("#noteText").val('')
          $("#editNote").prop('disabled', true)
          $("#deleteNote").prop('disabled', true)
          $("#selectedNote").removeAttr("name")
          getNotes()

root = exports ? this
root.selectNote = (tmp) ->
  id = +($(tmp).parents('tr:first').find('td:first').text())
  $("#errorBox").remove()
  $("#messageBox").remove()
  r = jsRoutes.controllers.Notes.getNote(id)
  $.ajax
    url:  r.url
    type: r.type
    context: this
    error: (jqXHR, textStatus, errorThrown) ->
      $( "<div id='errorBox' class='alert alert-danger fade in'>" +
          "<button type='button' class='close' data-dismiss='alert'>&times;</button>" + textStatus + "</div>")
      .insertBefore( "#addNote")
      $("#noteText").val('')
    success: (data, textStatus, jqXHR) ->
      $("#editNote").prop('disabled', false)
      $("#deleteNote").prop('disabled', false)
      $("#selectedNote").attr("name",data.id)
      $("#noteText").val(data.note)



