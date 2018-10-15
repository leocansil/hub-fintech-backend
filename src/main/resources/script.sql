DROP DATABASE IF EXISTS DB_HUB_FINTECH;
CREATE DATABASE DB_HUB_FINTECH;
USE DB_HUB_FINTECH;

CREATE TABLE pessoas (
	id_pessoa BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome_razao_social VARCHAR(100) NOT NULL,
        nome_fantasia VARCHAR(150) NULL,
        cpf_cnpj VARCHAR(15) NOT NULL,
        data_nascimento DATE NULL,
        tipo_pessoa VARCHAR(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE contas (
	id_conta BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_criacao DATE NOT NULL,
        id_pessoa BIGINT(20) NOT NULL,
        tipo_conta VARCHAR(20),
        status_conta VARCHAR(20),
        saldo DECIMAL(10,2),
        id_conta_pai BIGINT(20) ,
        FOREIGN KEY (id_conta_pai) REFERENCES contas(id_conta),
        FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE historico_movimentacao (
	id_movimentacao BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_movimentacao DATE,
	valor_movimentacao DECIMAL(10,2),
	tipo_operacao VARCHAR(20),
	descricao VARCHAR(200),
	id_conta_origem BIGINT(20),
	id_conta_destino BIGINT(20),
        FOREIGN KEY (id_conta_origem) REFERENCES contas(id_conta),
        FOREIGN KEY (id_conta_destino) REFERENCES contas(id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
