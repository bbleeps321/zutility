object ZUtilityForm: TZUtilityForm
  Left = 0
  Top = 0
  Caption = 'ZUtility'
  ClientHeight = 287
  ClientWidth = 426
  Color = clBlack
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  Menu = MainMenu
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object ZPlayerButton: TButton
    Left = 64
    Top = 104
    Width = 89
    Height = 33
    Caption = 'Open ZPlayer'
    TabOrder = 0
    OnClick = ZPlayerButtonClick
  end
  object ZEditorButton: TButton
    Left = 159
    Top = 104
    Width = 89
    Height = 33
    Caption = 'Open ZEditor'
    TabOrder = 1
    OnClick = ZEditorButtonClick
  end
  object MainMenu: TMainMenu
    object Run1: TMenuItem
      Caption = 'Run'
      object ZPlayer1: TMenuItem
        Caption = 'ZPlayer'
        OnClick = ZPlayerButtonClick
      end
      object ZEditor1: TMenuItem
        Caption = 'ZEditor'
        OnClick = ZEditorButtonClick
      end
    end
    object About1: TMenuItem
      Caption = 'About'
      OnClick = About1Click
    end
  end
end
