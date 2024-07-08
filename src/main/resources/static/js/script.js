document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('submit-button').addEventListener('click', function(e) {
        e.preventDefault();
        
        if (!check('referencia')
        || !check('emissor')
        || !check('valor_total')
        || !check('total_parcelas')) {
            return;
        }

        // Capturar os dados do formulário
        const formData = new FormData(document.querySelector('form'));
        const params = new URLSearchParams();
        for (const pair of formData) {
            const key = pair[0];
            let value = pair[1];
            if (key === 'valor_total') {
                value = value.replace(/R\$ /, '').replace(/,/,'.')
            }
            params.append(key, value);
        }
        generatePDF(params);
    });

    checkHelp('referencia')
    checkHelp('emissor')
    checkHelp('valor_total')
    checkHelp('total_parcelas')

    document.getElementById('valor_total').addEventListener('input', function(e) {
        let newValue = e.currentTarget.value.replace(/[^\d,]/g, '');
        const indexDecimal = newValue.indexOf(',');
        if ((newValue.length - indexDecimal) > 3) {
            newValue = newValue.replace(/^(\d+,)(\d\d)\d*/, '$1$2');
        }

        e.currentTarget.value = `R$ ${newValue}`;
    })

    document.getElementById('valor_total').addEventListener('focusout', function(e) {
        let newValue = e.currentTarget.value.replace(/[^\d,]/g, '');
        const indexDecimal = newValue.indexOf(',');
        const positionDecimal = newValue.length - indexDecimal;

        if (indexDecimal > -1 && positionDecimal < 3) {
            newValue = newValue + (positionDecimal === 2 ? '0' : '00')
        }else if (indexDecimal > -1 && positionDecimal > 3) {
            newValue = newValue.replace(/^(\d+,)(\d\d)\d*/, '$1$2');
        } else if (indexDecimal === -1 && newValue) {
            newValue = newValue + ',00'
        } else if (!newValue) {
            newValue = '0,00'
        }

        e.currentTarget.value = `R$ ${newValue}`;
    })

    document.getElementById('total_parcelas').addEventListener('input', function(e) {
        document.getElementById('numero_total_parcelas').innerText = e.currentTarget.value;
    })

});
function generatePDF(params) {
    fetch('generate?' + params.toString(), {
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    })
        .then(response => response.blob())
        .then(blob => {
            const fileURL = URL.createObjectURL(blob);
            window.open(fileURL, '_blank');
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function checkHelp(id) {
    
    document.getElementById(id).addEventListener('input change', () => {
        document.getElementById(`${id}_help`).innerText = '';
    });

    document.getElementById(id).addEventListener('blur', () => {
        check(id)
    });
}


function check(id) {
    const value = document.getElementById(id).value;
    if (!value) {
        document.getElementById(`${id}_help`).innerText = 'Este campo é de preenchimento obrigatório!';
        return false;
    }

    return true;
}